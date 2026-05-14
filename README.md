# FitExtreme

# FitExtreme

FitExtreme è una piattaforma e‑commerce dedicata al mondo del fitness, pensata per offrire un'esperienza semplice, veloce e intuitiva a tutti gli utenti che cercano integratori, attrezzatura sportiva, abbigliamento tecnico e prodotti per il benessere.

## 🚀 Funzionalità principali

- Catalogo prodotti completo e filtrabile
- Sistema di autenticazione utenti
- Carrello dinamico
- Gestione ordini
- Area personale utente
- Design responsive e moderno
- Pannello amministratore (gestione prodotti, categorie, utenti)

## 🛠️ Tecnologie utilizzate

- **Java** (Servlet/JSP)
- **Eclipse Dynamic Web Project**
- **Apache Tomcat**
- **MySQL**
- **HTML5 / CSS3 / JavaScript**
- **JDBC**
- **EGit / GitHub**

## 📦 Struttura del progetto


## ⚙️ Installazione

1. Clona il repository:
   ```bash
   git clone https://github.com/tuo-utente/FitExtreme.git

---

# 🔥 **README.md — Versione Tecnica (specifica per Eclipse + Tomcat + MySQL)**

```markdown
# FitExtreme — Dynamic Web Project

FitExtreme è un e‑commerce fitness sviluppato come **Dynamic Web Project** in Eclipse, con backend basato su **Servlet/JSP** e database **MySQL**.

## 🧩 Stack Tecnologico

- Java 8+
- Servlet/JSP
- Apache Tomcat 9/10
- MySQL 8
- JDBC
- HTML5, CSS3, JS
- Eclipse IDE
- EGit

## 📁 Struttura del progetto

- `/src` → Servlet, DAO, Model
- `/WebContent` → JSP, CSS, JS, immagini
- `/META-INF` → configurazioni
- `/WEB-INF` → web.xml, librerie

## 🗄️ Database

- Nome DB: `fitExtreme`
- File SQL incluso: `FitExtreme.sql`
- Tabelle principali:
  - `users`
  - `products`
  - `categories`
  - `orders`
  - `order_items`

## ⚙️ Configurazione JDBC

Modifica il file `DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
private static final String USER = "root";
private static final String PASSWORD = "password";


