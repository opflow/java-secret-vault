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

Follow the steps in program to create the vault block. The output of the example:

```shell
[+] Vault password: *********
[+] Enter your secret: 
[-] Your input secret: ***********
[-] Vault Block:
$ANSIBLE_VAULT;1.1;AES256
37303565356131323035633938613138386435646566323833303834326462626236653336346439
6131396435333562363831363538663365656263646236610a633730343432383262626533663431
34636363333832336633343165623138616364303965623065393364633061343265376161356462
6435646266343166650a343032313537376662353666386366323839346235393366623137613663
3136
[-] Vault Block have been copied to clipboard. Paste it somewhere and press [Enter] to exit.
```
