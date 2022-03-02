# Hello JPA 14

### 주요 내용

고급 매핑

- @MappedSuperclass 매핑 정보 상속

```sql
create table Member
(
    MEMBER_ID        bigint not null,
    createdBy        varchar(255),
    createdDate      timestamp,
    lastModifiedBy   varchar(255),
    lastModifiedDate timestamp,
    username         varchar(255),
    primary key (MEMBER_ID)
)
```

```sql
insert into Member (createdBy, createdDate, lastModifiedBy, lastModifiedDate, username, MEMBER_ID)
values (?, ?, ?, ?, ?, ?)
```

```sql
select member0_.MEMBER_ID        as member_i1_0_0_,
       member0_.createdBy        as createdb2_0_0_,
       member0_.createdDate      as createdd3_0_0_,
       member0_.lastModifiedBy   as lastmodi4_0_0_,
       member0_.lastModifiedDate as lastmodi5_0_0_,
       member0_.username         as username6_0_0_
from Member member0_
where member0_.MEMBER_ID = ?
```