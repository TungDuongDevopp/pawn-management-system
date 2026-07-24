package com.tungduong.pawnmanagementsystem.repository;

import com.tungduong.pawnmanagementsystem.model.Customer;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {

    private final List<Customer> customers = new ArrayList<>(List.of(
            new Customer(1L,"Nguyễn Văn A","a@gmail.com","0123456789","Hà Nội"),
            new Customer(2L,"Trần Thị B","b@gmail.com","0123776378","Nha Trang"),
            new Customer(3L,"Lê Văn C","c@gmail.com","0987263784","Huế"),
            new Customer(4L,"Lò Van D","d@gmail.com","0364632742","Đà Năng"),
            new Customer(5L,"Bàn Thị E","e@gmail.com","0786374834","Thái Nguyên"),
            new Customer(6L,"Bùi Văn F","f@gmail.com","0220394239","Hồ Chí Minh")
    ));

    private final Long nextId = (long) (customers.size()+1);

    public List<Customer> findAll() {
        return customers;
    }

    public Optional<Customer> findById(Long id) {
        for(Customer customer : customers){

            if(customer.getId().equals(id)){
                return Optional.of(customer);
            }

        }
        return Optional.empty();
    }

    public Customer save(Customer customer) {
        customer.setId(nextId);
        customers.add(customer);
        return customer;
    }

    public boolean delete (Long id){
        Optional<Customer> current = findById(id);
        if(current.isEmpty()){
            return false;
        }
        customers.remove(current.get());
        return true;

    }

    public Customer update (Customer customer){
        Optional<Customer> current = findById(customer.getId());

        if(current.isEmpty()){
            return null;
        }
        current.get().setName(customer.getName());
        current.get().setPhone(customer.getPhone());
        current.get().setEmail(customer.getEmail());
        current.get().setAddress(customer.getAddress());
        return current.orElse(null);

    }

}
