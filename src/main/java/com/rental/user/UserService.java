package com.rental.user;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.handler.CustomException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public ArrayList<User> getUserList(){
		ArrayList<User> list = new ArrayList<User>();
		if(userRepo.count()>0) {
			Iterator<User> it = userRepo.findAll().iterator();
			
			while(it.hasNext()) {
				list.add((User) it.next());
			}
		}	
		return list;
	}
	
	/*
	public String addUser(User user) throws CustomException {

		if (this.checkIfEmailAddrExists(user)==false) {
			System.out.println("HERE1");
			return "Email";
		}

		if (this.checkIfPhoneNoExists(user)==false) {
			System.out.println("HERE2");
			return "Phone";
		}

		if (this.checkIfUserNameExists(user)==false) {
			System.out.println("HERE3");
			return "Username";
		}

		user.setUserid(this.getNewUserId());
		userRepo.save(user);
		return "Added";
	}*/

	public boolean addUser(User user) throws CustomException {
		if(!this.checkIfEmailAddrExists(user) && !this.checkIfPhoneNoExists(user) && !this.checkIfUserNameExists(user)) {
			user.setUserid(this.getNewUserId());
			userRepo.save(user);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public int getNewUserId() {
		ArrayList<User> list = new ArrayList<User>();
		list = this.getUserList();
		int max=0;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getUserid() > max) {
				max = list.get(i).getUserid();
			}
		}
		return max+1;
	}
	
	
	public User getUserByUsername(String username) {
		ArrayList<User> list = userRepo.findByUsername(username);
		return  list.get(0);
	}

	public User getUserByEmailAddress(String emailaddr) {
		ArrayList<User> list = userRepo.findByUsername(emailaddr);
		return list.get(0);
	}

	public boolean checkIfUserExists(String username) {
        ArrayList<User> list = userRepo.findByUsername(username);
        if (list.size() == 0) {
            return false;
        } else {
            return true;
        }
	}
	
	public boolean checkIdUserExistsByEmail(String emailaddr) {
		ArrayList<User> list = userRepo.findByEmailaddr(emailaddr);
		if (list.size() == 0) {
            return false;
        } else {
            return true;
        }
	}

    public int getUserIdFromUsername(String username) {
        // Assuming user already exits

        ArrayList<User> list = userRepo.findByUsername(username);
        return list.get(0).getUserid();
	}
	
	public Boolean checkPasswordByUsername(String username, String password) {
		User user = getUserByUsername(username);
		if (user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean checkPasswordByEmail(String emailaddr, String password) {
		User user = getUserByEmailAddress(emailaddr);
		if (user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	
	
	
	
	//.................................Backend Validations............................................
	
	
	
	public boolean checkIfEmailAddrExists(User user) throws CustomException {
		
		int temp = 0;
		ArrayList<User> list = new ArrayList<User>();
		list = this.getUserList();
		
		for(int i=0;i<list.size();i++) {
			if(user.getEmailaddr().equals(list.get(i).getEmailaddr())) {
				temp = 1;
				throw new CustomException("Email address Already exists");
			}
		}
		if(temp == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkIfUserNameExists(User user) throws CustomException {
		
		int temp = 0;
		ArrayList<User> list = new ArrayList<User>();
		list = this.getUserList();
		
		for(int i=0;i<list.size();i++) {
			if(user.getUsername().equals(list.get(i).getUsername())) {
				temp = 1;
				throw new CustomException("User Name Already exists");
			}
		}
		if(temp == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean checkIfPhoneNoExists(User user) throws CustomException {
		
		int temp = 0;
		ArrayList<User> list = new ArrayList<User>();
		list = this.getUserList();
		
		for(int i=0;i<list.size();i++) {
			if(user.getPhone().equals(list.get(i).getPhone())) {
				temp = 1;
				throw new CustomException("Phone Number Already exists");
			}
		}
		if(temp == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	
}
