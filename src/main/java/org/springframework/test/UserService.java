package org.springframework.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserService {
    private Map<Long, User> users = new HashMap<>();

    private Map<Long, List<User>> referrerUserMap = new HashMap<>();


    private static final double COMMISSION_RATE = 0.6;

    public void registerUser(Long userId, String userName, Long referrerId) {
        if (!users.containsKey(referrerId)) {
            System.out.println("推荐人不存在");
            return;
        }

        User newUser = new User(userId, userName, referrerId, 0);
        users.put(userId, newUser);
        upgradeLevel(newUser.getReferrerId(), newUser.getLevel());
    }


    private void upgradeLevel(Long userId, Integer level) {
        //本人
        User user = users.get(userId);

        if (user.getLevel() > level) {
            //已经升级
            return;
        }
        //下级
        List<User> userList = referrerUserMap.get(userId);

        long count = userList.stream().filter(temp -> Objects.equals(temp.getLevel(), user.getLevel())).count();
        if (count < 3) {
            //人数不到
            return;
        }
        int upgradeLevel = user.getLevel() + 1;
        user.setLevel(upgradeLevel);

        upgradeLevel(user.getReferrerId(), upgradeLevel);
    }


    public void consume(Long transactionId, Long userId, double amount) {
        if (!users.containsKey(userId)) {
            System.out.println("用户不存在");
            return;
        }

        Transaction transaction = new Transaction(transactionId, userId, amount);
        calculateCommission(transaction);
    }

    private void calculateCommission(Transaction transaction) {
        double totalCommission = transaction.getAmount() * COMMISSION_RATE;
        User currentUser = users.get(transaction.getUserId());
        while (currentUser.getReferrerId() != 0) {
            User referrer = users.get(currentUser.getReferrerId());
            if (referrer.getLevel() == 0) {
                break;
            }
            double referrerCommission = 0;
            switch (referrer.getLevel()) {
                case 1:
                    referrerCommission = transaction.getAmount() * 0.2;
                    totalCommission -= referrerCommission;
                    break;
                case 2:
                    referrerCommission = transaction.getAmount() * 0.3;
                    totalCommission -= referrerCommission;
                    break;
                case 3:
                    referrerCommission = transaction.getAmount() * 0.4;
                    totalCommission -= referrerCommission;
                    break;
                case 4:
                    referrerCommission = transaction.getAmount() * 0.5;
                    totalCommission -= referrerCommission;
                    break;
                case 5:
                    referrerCommission = transaction.getAmount() * 0.6;
                    totalCommission -= referrerCommission;
                    break;
            }
            //先用时间戳当主键
            Commission commission = new Commission(System.currentTimeMillis(), transaction.getTransactionId(), transaction.getUserId(), referrerCommission);
            //入库

            if (totalCommission <= 0) {
                break;
            }
            currentUser = referrer;
        }
    }
}
