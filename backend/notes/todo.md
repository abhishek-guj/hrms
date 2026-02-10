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