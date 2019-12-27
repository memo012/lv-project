# web端后台接口

## **注意：** 

文档中的结果数据仅供参考

## 测试用户

| 用户账号   | 密码   | 用户角色 | 备注         |
| ---------- | ------ | -------- | ------------ |
| 1713011332 | 12345  | 1        | 学生         |
| T171301    | 123456 | 4        | 教学院副院长 |
| T171305    | 123456 | 4        | 教学院副院长 |
| T171302    | 123456 | 3        | 副书记       |
| T171303    | 123456 | 3        | 副书记       |
| T171304    | 123456 | 2        | 班主任       |

## 用户角色

| 角色号 | 角色名称  | 备注   |
| ------ | --------- | ------ |
| 1      | user      | 学生   |
| 2      | teacher   | 老师   |
| 3      | secretary | 书记   |
| 4      | dean      | 院长   |
| 5      | admin     | 管理员 |

## 登录(统一登录接口)

1. 接口名： `api/v1/login/userLogin`

2. 请求方式： `POST`

3. 接口方法：

   ```Java
   public JSONResult userLogin(@RequestBody UserLoginRequest userLoginRequest)；
   ```

   ```Java
   请求样列：（必填项）
   {
   	"lvUserNum": "T171304",
   	"lvUserPassword":"123456"
   }
   ```

   ​	**参数含义：**

   ```Java
   lvUserNum：  用户账号
   lvUserPassword： 用户密码
   ```

   

4. 返回类型(JOSN格式)  -- 格式如下图所示

   ```java 
   {
       "status": 200,
       "msg": "OK",
       "data": {
           "id": "1575779028",
           "lvUserNum": "T171304",
           "lvUserName": "赵利辉",
           "lvUserPhone": "15383466859",
           "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzU4NjU0MjgsInVzZXJuYW1lIjoiVDE3MTMwNCJ9.MqvbMeJYHuMfLVp6xS1tWv99m6cau2keC484GJmA4XY",
           "roleNum": 2,
           "lvCollage": 13
       },
       "ok": null
   }
   ```

5. 注意:

   - status: 表示状态码(共有5种状态码)  (核心)
     - 200: 表示请求成功
     - 401: 表示查询为空
     - 402: 表示参数请求错误(顺序,非空等判断)
     - 555: 表示异常错误(日期异常)
     - 502： 登录过期 ，重新登录
   - 504： 权限不足
   - 返回数据字段含义
     - `lvUserNum` ： 学生学号或老师工号
     - `lvUserName` ： 学生姓名或老师姓名
     - `lvUserPhone` ： 学生手机号或老师手机号
     - `token`： 令牌(身份验证)
     - `roleNum` : 用户角色
     - `lvCollage` : 所处学院

## web端老师未审核和历史列表

1. **接口名:** `api/v1/web/checkMessage`

2. 请求方式: `POST`

3. 请求接口方法:

   ```Java
   public JSONResult checkMessage(@RequestBody WebTeacherCheckDTO webTeacherCheckDTO)
   ```

4. 请求参数:(参数)

```Java
请求样列：（必填项）
{
	"account":"T171304",          
	"type":0,
	"pageNum":1,
	"pageSize":2
}
--------------------------------------
请求样列：（必填+选填项）
{
	"account":"T171304",                  
	"type":1,                                          
	"pageNum":1,                             
	"pageSize":2，
	"college":
	"className":
	
}
```

注意:

- 请求参数详解：
  - `account` : 账号
  - `type`: 类型  只允许填写 0 或 1  解释如下
    - 0 :  表示 历史列表
    - 1 : 表示未审核列表
  - `pageNum` : 当前页
  - `pageSize`: 页面大小
  - `college` : 所属院校
  - `className`: 所在班级(学生)

5. 返回类型(JOSN格式)  -- 格式如下图所示

   ```Java
   {
       "status": 200,
       "msg": "OK",
       "data": {
           "page": 1,
           "total": 1,
           "records": 2,
           "rows": [
               {
                   "id": "1577263255",
                   "userName": "白山",
                   "lvUserNum": "1713011332",
                   "lvBeginTime": "2019-12-25 17:06:50",
                   "lvEndTime": "2019-12-27 7:06:50",
                   "lvNum": null,
                   "taskId": null,
                   "lvId": "1577180721",
                   "lvRelativeType": "父亲",
                   "lvRelativeName": "白石山",
                   "lvRelativePhone": "15383466853",
                   "lvApplyDetail": null,
                   "lvReason": "比赛",
                   "lvStatus": "ing"
               },
               {
                   "id": "1577263255",
                   "userName": "白山",
                   "lvUserNum": "1713011332",
                   "lvBeginTime": "2019-12-6 17:06:50",
                   "lvEndTime": "2019-12-8 17:06:50",
                   "lvNum": null,
                   "taskId": null,
                   "lvId": "1575722433",
                   "lvRelativeType": "父亲",
                   "lvRelativeName": "白石山",
                   "lvRelativePhone": "15383466853",
                   "lvApplyDetail": null,
                   "lvReason": "比赛",
                   "lvStatus": "pass"
               }
           ]
       },
       "ok": null
   }
   ```

   注意:

   - `status`: 有3种形式

     - 401: 表示查询为空
     - 402: 表示参数请求错误(顺序,非空等判断)
     - 200: 请求成功
     -  502： 登录过期 ，重新登录

     - 504： 权限不足

   - 返回数据中有5个字段名,表示含义如下

     - 字段名`lvStatus` 表示请假状态,有三种表示形式
       - `ing` : 审核中
       - `pass` : 通过
       - `reject` : 驳回(拒绝)
     - 字段名`lvId`表示请假id
     - 字段名`lvBeginTime` 表示请假开始时间
     - 字段名`lvEndTime` 表示请假截止时间
     - 字段名`lvReason` 表示请假原因
     - 字段名`lvApplyDetail` 表示请假详情
     - 字段名`lvRelativePhone` 表示亲属手机
     - 字段名`lvRelativeName` 表示亲属姓名
     - 字段名`lvRelativeType` 表示亲属类型
     - 字段名`lvUserNum` 表示请假人学号
     - 字段名`userName` 表示请假人姓名
     - 字段名`page`表示当前页数
     - 字段名`records` 表示总记录数
     - 字段名`rows` 表示数据

## web端消息通知

1. **接口名:** `api/v1/web/findMessageList`

2. 请求方式: `GET`

3. 请求接口方法:

   ```Java
   public JSONResult findMessageList(@RequestParam("account") String account)
   ```

4. 请求参数:(参数)

```Java
account： 老师工号或学生学号
```

5. 返回类型(JOSN格式)  -- 格式如下图所示

```Java
{
    "status": 200,
    "msg": "OK",
    "data": [
        {
            "lvMessageId": null,
            "lvStatus": "pass",
            "createTime": "2019-12-08 03:09:21",
            "lvId": "1575722433",
            "lvBook": 1,
            "userName": null,
            "taskId": null
        },
        {
            "lvMessageId": null,
            "lvStatus": "pass",
            "createTime": "2019-12-08 01:01:08",
            "lvId": "1575722193",
            "lvBook": 0,
            "userName": null,
            "taskId": null
        }
    ],
    "ok": null
}
```

注意:

- `status`: 有5种形式
- 401: 表示查询为空
  - 402: 表示参数请求错误(顺序,非空等判断)
  - 200: 请求成功
  -  502： 登录过期 ，重新登录
  
- 504： 权限不足
  
- 返回数据中有6个字段名,表示含义如下

  - 字段名`lvStatus` 表示请假状态,有三种表示形式
    - `ing` : 审核中
    - `pass` : 通过
    - `reject` : 驳回(拒绝)
  - 字段名`lvId`表示请假id  （标记已读或删除使用 -- 学生）
  - 字段名`createTime` 表示请假消息创建时间
  - 字段名`lvBook` 表示请假消息是否已读
  - 字段名`userName` 表示请假人姓名(老师端使用)
  - 字段名`taskId`  表示请假审核id
  - 字段名`lvMessageId` 表示消息id （标记已读或删除使用 -- 老师）

## web端消息通知已读(单条)

1. **接口名:** `api/v1/web/markMsgIsRead`

2. 请求方式: `GET`

3. 请求接口方法:

   ```Java
   public JSONResult markMsgIsRead(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType)
   ```

4. 请求参数:(参数)

```Java
lvMessageId： 消息id
accountType： 账号类型
```

**请求参数解释：**

- `lvMessageId `详解
  - 若登录者为老师， 传入的`lvMessageId` 的值为`lvMessageId`  值
  - 若登录着为学生，传入的`lvMessageId` 的值为`lvId`  值
- `accountType` 详解
  - 若登录者为老师，传入的`accountType `的值为 整型 1
  - 若登录者为学生，传入的`accountType` 的值为 整型 0 

5. 返回类型(JOSN格式)  -- 格式如下图所示

```Java
{
    "status": 200,
    "msg": "OK",
    "data": null,
    "ok": null
}
```

注意:

- `status`: 有5种形式
- 401: 表示查询为空
- 402: 表示参数请求错误(顺序,非空等判断)
- 200: 请求成功
- 502： 登录过期 ，重新登录
- 504： 权限不足

## web端消息通知删除(单条)

1. **接口名:** `api/v1/web/markMsgDelete`

2. 请求方式: `GET`

3. 请求接口方法:

   ```Java
   public JSONResult markMsgIsRead(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType)
   ```

4. 请求参数:(参数)

```Java
lvMessageId： 消息id
accountType： 账号类型
```

**请求参数解释：**

- `lvMessageId `详解
  - 若登录者为老师， 传入的`lvMessageId` 的值为`lvMessageId`  值
  - 若登录着为学生，传入的`lvMessageId` 的值为`lvId`  值
- `accountType` 详解
  - 若登录者为老师，传入的`accountType `的值为 整型 1
  - 若登录者为学生，传入的`accountType` 的值为 整型 0 

5. 返回类型(JOSN格式)  -- 格式如下图所示

```Java
{
    "status": 200,
    "msg": "OK",
    "data": null,
    "ok": null
}
```

注意:

- `status`: 有5种形式
- 401: 表示查询为空
- 402: 表示参数请求错误(顺序,非空等判断)
- 200: 请求成功
- 502： 登录过期 ，重新登录
- 504： 权限不足

## web端消息通知已读(全部)

1. **接口名:** `api/v1/web/markAllMsgIsRead`

2. 请求方式: `GET`

3. 请求接口方法:

   ```Java
   public JSONResult markAllMsgIsRead(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType)
   ```

4. 请求参数:(参数)

```Java
lvMessageId： 消息id
accountType： 账号类型
```

**请求参数解释：**

- `lvMessageId `详解
  - 若登录者为老师， 传入的`lvMessageId` 的值为`lvMessageId`  值
  - 若登录着为学生，传入的`lvMessageId` 的值为`lvId`  值
- `accountType` 详解
  - 若登录者为老师，传入的`accountType `的值为 整型 1
  - 若登录者为学生，传入的`accountType` 的值为 整型 0 

5. 返回类型(JOSN格式)  -- 格式如下图所示

```Java
{
    "status": 200,
    "msg": "OK",
    "data": null,
    "ok": null
}
```

注意:

- `status`: 有5种形式
- 401: 表示查询为空
- 402: 表示参数请求错误(顺序,非空等判断)
- 200: 请求成功
- 502： 登录过期 ，重新登录
- 504： 权限不足

## web端消息通知删除(全部)

1. **接口名:** `api/v1/web/markAllMsgDelete`

2. 请求方式: `GET`

3. 请求接口方法:

   ```Java
   public JSONResult markAllMsgDelete(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType)
   ```

4. 请求参数:(参数)

```Java
lvMessageId： 消息id
accountType： 账号类型
```

**请求参数解释：**

- `lvMessageId `详解
  - 若登录者为老师， 传入的`lvMessageId` 的值为`lvMessageId`  值
  - 若登录着为学生，传入的`lvMessageId` 的值为`lvId`  值
- `accountType` 详解
  - 若登录者为老师，传入的`accountType `的值为 整型 1
  - 若登录者为学生，传入的`accountType` 的值为 整型 0 

5. 返回类型(JOSN格式)  -- 格式如下图所示

```Java
{
    "status": 200,
    "msg": "OK",
    "data": null,
    "ok": null
}
```

注意:

- `status`: 有5种形式
- 401: 表示查询为空
- 402: 表示参数请求错误(顺序,非空等判断)
- 200: 请求成功
- 502： 登录过期 ，重新登录
- 504： 权限不足