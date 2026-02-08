## create sets instead of lists for relationship fields

---

## many to many = create 2 table entities and one junction table entity [good for production]

```java
// owner of relation between post<->tags will have cascade and orphan removal
@OneToMany(
        mappedBy="post",
        cascade=CascadeType.All,
        orhanRemoval = true
)
private Set<PostTag> postTags = new HashSet<>();
```

```java
// junction table must have 2 many-to-one relations
// also in many to one relatoins, 
// there's no need of making a set just single entity will be there so no need,
// add fetchtype and foreignkey
@ManyToOne(fetch = FetchType.Lazy)
@JoinColumn(
        name = "post_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_post_tag_post") // better to specify foreignkey name 
)
private Post post;

@ManyToOne(fetch = FetchType.Lazy)
@JoinColumn(
        name="tag_id",
        nullable = false,
        foreignKey = @ForeignKey(name="fk_post_tag_tag")
)
private Tag tag;
```


```java
// now in tags just make one to many referncing posttag [junctiion table
@OneToMany(mappedBy = "tag")
private Set<PostTag> postTags = new HashSet<>();
```
--- 

where the foreign key is going to be stored, 
that entity must have @JoinColumn and the other side entity must have mappedBy

```java
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "employee_profile_id", nullable = false, unique = true)
private EmployeeProfile employeeProfile;
```
```java
@OneToOne(mappedBy = "employee_profile", fetch = FetchType.LAZY)
private User user;
```

---
## Many to one example [inverse of one to many]
### example of one role many employees
### inverse many employess can have same roles
```java
@ManyToOne(fetch= FetchType.Lazy)
@JoinColumn(name = "role_id", nullable=false) // always refernce the refrence entity
private Role role;
```

--- 

## MessageDigest [used to hash password]
- provides applications the functionality of a message digest algorithm, such as SHA-1 or SHA-256
- secure one-way hash functions

---

## To send api response 
- wrap in ApiResponse and send.
- always add ResponseEntity.status()
```java
return ResponseEntity.status().body(new ApiResponse<>(msg:"msg",data:null,meta_data:null));
```


