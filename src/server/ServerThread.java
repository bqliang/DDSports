package server;

import model.Agreement;
import model.Transfer;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author bqliang
 */

public class ServerThread implements Runnable, Agreement {

    private Socket s;
    private Transfer feedback;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerThread(Socket socket){
        s = socket;
    }

    @Override
    public void run() {
        try{
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
            Transfer receive = (Transfer) ois.readObject();

            switch (receive.getCommand()){
                case REGISTER:{
                    feedback = DbHandler.register(receive.getUser());
                    break;
                }

                case USER_LOGIN:{
                    User user = receive.getUser();
                    feedback = DbHandler.userLogin(user);
                    break;
                }

                case ADMIN_LOGIN:{
                    feedback = DbHandler.adminLogin(receive.getAdmin());
                    break;
                }

                case AUTHENTICATE:{
                    feedback = DbHandler.authenticate(receive.getUser());
                    break;
                }

                case CREATE_ACTIVITY:{
                    feedback = DbHandler.createActivity(receive.getActivity());
                    break;
                }

                case JOIN:{
                    feedback = DbHandler.join(receive.getUser(),receive.getActivity());
                    break;
                }

                case CHECK_IN:{
                    feedback = DbHandler.checkIn(receive.getUser(), receive.getActivity());
                    break;
                }

                case VIEW_ACTIVITY:{
                    feedback = DbHandler.viewActivity(receive.getActivity());
                    break;
                }

                case VIEW_USER:{
                    feedback = DbHandler.viewUser(receive.getUser());
                    break;
                }

                case VIEW_CERTIFICATE_USERS:{
                    feedback = DbHandler.viewCertificateUsers();
                    break;
                }

                case VIEW_ACTIVITIES:{
                    feedback = DbHandler.viewActivities();
                    break;
                }

                case SEND_CODE:{
                    feedback = DbHandler.sendLoginCode(receive.getUser());
                    break;
                }

                case LOGIN_BY_EMAIL:{
                    feedback = DbHandler.loginByCode(receive.getUser());
                    break;
                }

                case FILTER_ACTIVITIES:{
                    feedback = DbHandler.filterActivities(receive.getSql());
                    break;
                }

                case RETRIEVE_PASSWORD:{
                    feedback = DbHandler.retrievePassword(receive.getUser());
                    break;
                }

                case RESET_PASSWORD:{
                    feedback = DbHandler.resetPwByPw(receive);
                    break;
                }

                case EDIT_PROFILE:{
                    feedback = DbHandler.editProfile(receive.getUser());
                    break;
                }

                case DELETE_ACTIVITY:{
                    feedback = DbHandler.deleteActivity(receive.getActivity());
                    break;
                }

                case EXIT_ACTIVITY:{
                    feedback = DbHandler.exitActivity(receive.getUser(), receive.getActivity());
                    break;
                }

                default:{

                }
            }

            oos.writeObject(feedback);
            oos.flush();
            close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 关闭相关资源
     * @throws IOException
     */
    private void close() throws IOException {
        ois.close();
        oos.close();
        s.close();
    }

}
