const admin = require('firebase-admin');
const serviceAccount = require('./fc-chatbot-todo-firebase-adminsdk-q5fkd-4003822dfe.json');
const credential = admin.credential.cert(serviceAccount);
admin.initializeApp({
    credential
});

const db = admin.firestore();

// 추가
const testData = {
    desc: '장보기',
    due_date: new Date('2018-03-25'),
    is_done: true
};

// db
// 	.collection('todos')
// 	.add(testData)
// 	.then(result => {
// 		console.log(result);
// 	})
// 	.catch(err => {
// 		console.error(err);
// 	});

// db.collection("cities")
// .doc("LA")
// .set({
//     name: "Los Angeles",
//     state: "CA",
//     country: "USA"
// })




// 조회

// db
// 	.collection('todos')
// 	.where('desc', '==', '장보기')
// 	.limit(2)
// 	.get()
// 	.then(results => {
// 		const todos = [];
// 		results.forEach(doc =>
// 			todos.push(Object.assign({ id: doc.id }, doc.data()))
// 		);
// 		console.log(todos);
// 	})
// 	.catch(err => console.log(err));

// 단일 조회
// db.collection('cities')
//   .doc('LA')
//   .get()
//   .then(result => {
//     if (!result || !result.data()) return
//     const todo = Object.assign({id: result.id}, result.data())
//     console.log(todo)
//   })
//   .catch(err => console.log(err))

// 데이터 수정

const updateData = {
    is_done: false
};

// db
//     .collection('todos')
//     .doc('QYgWxlp0F2zE83VxXNE2')
//     .update(updateData)
//     .then(result => {
//         console.log('success to update todo')
//     })
//     .catch(err => console.log(err))

// 삭제

// db
// 	.collection('todos')
// 	.doc('p9BvnwBBjNQUlGwnjXK5')
// 	.delete()
// 	.then(function() {
// 		console.log('Document successfully deleted!');
// 	})
// 	.catch(function(err) {
// 		throw err;
// 	});