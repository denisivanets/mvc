package business;

public class Model {
    Client currentClient;

    public AccountType checkLogin(String login,char[] password){
        Client client = FileUtils.findClientInFileByLogin(login);
        if(client != null){
            if(password.equals(client.getPassword())){
                currentClient = client;
                return client.getAccountType();
            }
            else{
                return AccountType.UNKNOWN;
            }
        }//if
        return AccountType.UNKNOWN;
    }



    private String parsePassword(char[] password){
        String result = "";
        for(char oneChar : password){
            result += oneChar;
        }
        return result;
    }

}
