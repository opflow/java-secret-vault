# java-secret-vault

## Usage

### How to generate the vault block

Create a vault password file or vault password shell script in the current directory;

```shell
mkdir example; cd example
echo "Tops3cr3t" > passwd.txt
```

Download the java-secret-vault.jar program

```shell
curl https://raw.githubusercontent.com/opflow/java-secret-vault/master/download.sh | bash
```

Execute the program with the vault password file (level: strong, medium, simple - simple is default):

```shell
java \
-Djava.secret.vault.password.level=medium \
-Djava.secret.vault.password.file=$PWD/passwd.txt \
-jar java-secret-vault.jar;
```

Follow the steps in program to create the vault block.
