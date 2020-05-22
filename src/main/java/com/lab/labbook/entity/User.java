package com.lab.labbook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String role = Role.USER.name();

    private boolean blocked;
    private boolean observer;
    private LocalDateTime date = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<LabBook> labBooks = new ArrayList<>();

    public static class UserBuilder {
        private Long id;
        private String name;
        private String lastName;
        private String login;
        private String email;
        private String password;
        private boolean blocked;
        private boolean observer;
        private String role = Role.USER.name();
        private LocalDateTime date = LocalDateTime.now();
        private List<LabBook> labBooks = new ArrayList<>();

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder blocked(boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public UserBuilder observer(boolean observer) {
            this.observer = observer;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public UserBuilder addLabBook(LabBook labBook) {
            this.labBooks.add(labBook);
            return this;
        }

        public User build() {
            return new User(id, name, lastName, login, email, password, blocked, observer, role, date, labBooks);
        }
    }

    private User(Long id, String name, String lastName, String login, String email,
                 String password, boolean blocked, boolean observer, String role, LocalDateTime date, List<LabBook> labBooks) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.blocked = blocked;
        this.observer = observer;
        this.role = role;
        this.date = date;
        this.labBooks = labBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return blocked == user.blocked &&
                observer == user.observer &&
                Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, login, email, password, blocked, observer, role);
    }
}