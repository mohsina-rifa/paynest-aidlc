1. Create Category.java as a plain entity first : you did it in User.java (the "standalone" side of your 1-to-1)

@Entity, @Id, @GeneratedValue(strategy = IDENTITY), plus a name field. No relationship annotations yet. Get this saving on its own before you connect anything.
___
2. Create Transaction.java the same way : same as step 1

@Id + @GeneratedValue, an amount as BigDecimal (like Wallet.balance), maybe a description. Still no relationship. Note that you forgot @GeneratedValue on Wallet.java:21 — that's the bug to not repeat twice here.
___
3. Add the @ManyToMany + @JoinTable on Transaction : you did @OneToOne + @JoinColumn in Wallet.java:28-30

This is the real difference. A 1-to-1 needs only a foreign key column, so @JoinColumn was enough. Many-to-many can't be expressed in a column — it needs a third table:
@ManyToMany
@JoinTable(
name = "transaction_category",
joinColumns = @JoinColumn(name = "transaction_id"),
inverseJoinColumns = @JoinColumn(name = "category_id")
)
private Set<Category> categories = new HashSet<>();
joinColumns points back at this entity, inverseJoinColumns at the other one. Use Set, not List — with List Hibernate deletes and re-inserts every row on any change. Initialize it to new HashSet<>() inline so it's never null.

Whichever side holds @JoinTable is the owning side. Start with just this one side and stop here for a bit — a unidirectional many-to-many is fully functional and half the trouble.
___
4. Create CategoryRepository and TransactionRepository : you did it in UserRepository.java

Identical — extends JpaRepository<Category, Integer>, empty body. No change in thinking here.
___
5. Create CategoryService and CategoryController for plain CRUD : you did it in UserService.java / UserController.java

Same four methods. Two fixes to carry over: use @RestController not @Controller, and put @PathVariable on the id parameters — both of those bugs are in UserController.java right now.
___
6. Create TransactionService with @Transactional on the write methods : UserService.java has no transaction boundary at all

Now it actually matters. Attaching a category is read-modify-write across two entities, and you want it atomic.
___
7. Add the link/unlink operations — this is the part your 1-to-1 had no equivalent for

POST /transactions/{id}/categories/{categoryId} and the matching DELETE. Inside: load both, then transaction.getCategories().add(category). Because Transaction is the owning side, Hibernate writes the join row when the transaction commits — you never touch the join table directly, and you never call save() on Category.
___
8. Decide about cascade — and mostly, don't use it

You didn't set any on Wallet, which was correct. Here it's a trap worth naming: cascade = ALL on a many-to-many means deleting one transaction deletes the shared "Utility" category out from under every other transaction. Leave cascade off. Deleting a Transaction removes only its join rows, which is what you want.
___
9. Add equals/hashCode to Category based on a business key, not the id

New concern that 1-to-1 let you ignore. Set needs them, and the default identity-based ones break when an entity goes from transient (null id) to persisted. Use the name field. Related: do not put Lombok @Data or @ToString on these — @ToString on both sides of a bidirectional relationship stack-overflows.
___
10. Return DTOs from the controller — here it stops being optional

With User you got away with returning the entity. Here you won't: @ManyToMany is LAZY by default (unlike @OneToOne, which is EAGER — that's why Wallet never showed you this). Serializing a lazy collection outside the transaction throws LazyInitializationException, and if you later make it bidirectional, Jackson recurses infinitely between the two sides. A TransactionResponse record holding List<String> categoryNames sidesteps  both.
___
11. Only then, if you want it, add the inverse side to Category
    @ManyToMany(mappedBy = "categories")
    private Set<Transaction> transactions = new HashSet<>();
    mappedBy says "the other class owns this; don't create a second join table." The classic beginner bug is adding to category.getTransactions() and finding nothing saved — changes to the inverse side are simply ignored. Write a helper method on Transaction that updates both collections so they can't drift apart in memory.
