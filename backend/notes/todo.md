--- 

add this into entities = 

```db2
Table game.slot_size{
pk_slot_size_id bigint [pk]
count varchar
}

Table game.game_slot_size{
slot_size_id bigint [ref: > game.slot_size.pk_slot_size_id]
game_type_id bigint [ref: > game.game_types.pk_game_type_id]
}
```


---
add: instead of modelMapper use custome code mapper. 
---
add: @pre-authorize annotation above endpoints
---
add: this at table level, so when <?>jpaRepostiry.delete() calss this inse=tead of hard delete.
    @SQLDelete(sql = update travelplan set is_deleted = true, delete_on currenttimestamp where pktrabelplanid = ? ) 
---
add: where annotation in table to filter by is_deleted = false
NOTE but would need to write native queries to get is_deleted=true rows as per requirements
---
add: @PrePersist and @PreUpdate annotation to run methods like validateDate.
    @prePersist and @Preupdate, we can anotate methods to auto run when doing jap operations in  db
---
add: softDelete methods instead of hard-delete
---
add: Builder.Default so that mapped properties are initialised and no nulexpressionpointer error comes
---
add: new endpoints after basic crud -> pagination, sort, filter, upcoming.
---