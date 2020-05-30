db.createUser(
    {
      user: "admin",
      pwd: "admin",
      roles: [ "readWrite", "dbAdmin" ]
    }
)

db.createCollection("users")
db.createCollection("notes");
db.createCollection("notebooks");
