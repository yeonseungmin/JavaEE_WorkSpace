<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="zxx">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ashion Template">
    <meta name="keywords" content="Ashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Ashion | Template</title>

    <%@ include file="../inc/head_link.jsp" %>
</head>

<body>
    <!-- Page Preloder -->
    <%@ include file="../inc/preloader.jsp" %>

    <!-- Offcanvas Menu Begin -->
    <div class="offcanvas-menu-overlay"></div>
    <div class="offcanvas-menu-wrapper">
        <div class="offcanvas__close">+</div>
        <ul class="offcanvas__widget">
            <li><span class="icon_search search-switch"></span></li>
            <li><a href="#"><span class="icon_heart_alt"></span>
                <div class="tip">2</div>
            </a></li>
            <li><a href="#"><span class="icon_bag_alt"></span>
                <div class="tip">2</div>
            </a></li>
        </ul>
        <div class="offcanvas__logo">
            <a href="./index.html"><img src="/static/template/img/logo.png" alt=""></a>
        </div>
        <div id="mobile-menu-wrap"></div>
        <div class="offcanvas__auth">
            <a href="#">Login</a>
            <a href="#">Register</a>
        </div>
    </div>
    <!-- Offcanvas Menu End -->

    <!-- Header Section Begin -->
    <%@ include file="../inc/header.jsp" %>
    <!-- Header Section End -->
	
	<!-- BreadCrumb Begin -->
	<div class="breadcrumb-option">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="breadcrumb__links">
                        <a href="./index.html"><i class="fa fa-home"></i> Home</a>
                        <span>Shopping cart</span>
                    </div>
                </div>
            </div>
        </div>
    </div>	
    <!-- BreadCrumb End -->
    
    <!-- Cart Begin -->
	<section class="shop-cart spad" id="app">
	        <div class="container">
	            <div class="row">
	                <div class="col-lg-12">
	                    <div class="shop__cart__table">
	                        <table>
	                            <thead>
	                                <tr>
	                                    <th>Product</th>
	                                    <th>Price</th>
	                                    <th>Quantity</th>
	                                    <th>Total</th>
	                                    <th></th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	
	                            	<!-- 아래의 div 안에 영역을 Vue 의 영향력 하에 두겠다 
	                            		아래의 반복문의 앞에 명시한 소괄호의 (인수1, 인수2)
	                            		인수1: 배열내에서의 한 요소 
	                            		인수2 : 배열의 반복횟수인 index
	                            		
	                            		:key의 역할은 가상돔을 효율적으로 처리하기 위해서는 개발자가 반복문 수행시 
	                            		해당 돔객체에 유일한 키값을 부여하는것이 좋다..
	                            		아래에서는 유일한 값이 상품의 pk이므로, key값으로 활용함 
	                            	-->
	                            	
	                                <tr v-for="(cart, index) in cartList" :key="cart.product_id">
	                                    <td class="cart__product__item">
	                                        <img src="img/shop-cart/cp-1.jpg" alt="">
	                                        <div class="cart__product__item__title">
	                                            <h6>{{cart.product_name}}</h6>
	                                        </div>
	                                    </td>
	                                    <td class="cart__price">{{moneyFormat(cart.price)}}</td>
	                                    <td class="cart__quantity">
	                                        <div class="pro-qty"><span class="dec qtybtn">-</span>
	                                            <input type="text" v-model="cart.ea">
	                                        <span class="inc qtybtn">+</span></div>
	                                    </td>
	                                    <td class="cart__total">{{ moneyFormat(cart.price*cart.ea) }}</td>
	                                    <td class="cart__close"><span class="icon_close" @click="removeItem(cart.product_id)"></span></td>
	                                </tr>
	                            	
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <div class="row">
	                <div class="col-lg-6 col-md-6 col-sm-6">
	                    <div class="cart__btn">
	                        <a href="#">Continue Shopping</a>
	                    </div>
	                </div>
	                <div class="col-lg-6 col-md-6 col-sm-6">
	                    <div class="cart__btn update__btn">
	                        <a href="#"><span class="icon_loading"></span> Update cart</a>
	                    </div>
	                </div>
	            </div>
	            <div class="row">
	                <div class="col-lg-6">
	                    <div class="discount__content">
	                        <h6>Discount codes</h6>
	                        <form action="#">
	                            <input type="text" placeholder="Enter your coupon code">
	                            <button type="submit" class="site-btn">Apply</button>
	                        </form>
	                    </div>
	                </div>
	                <div class="col-lg-4 offset-lg-2">
	                    <div class="cart__total__procced">
	                        <h6>Cart total</h6>
	                        <ul>
	                            <li>Subtotal <span>$ 750.0</span></li>
	                            <li>Total <span>$ 750.0</span></li>
	                        </ul>
	                        <a href="#" class="primary-btn">Proceed to checkout</a>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </section>
	<!-- Cart End -->
	
	<!-- Instagram Begin -->
	<%@ include file="../inc/insta.jsp" %>
	<!-- Instagram End -->
	
	<!-- Footer Section Begin -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- Footer Section End -->
	
	<!-- Js Plugins -->
	<%@ include file="../inc/footer_link.jsp" %>
	<!-- Vue를 이용하면 개발자가 DOM 렌더링 시 전통적인 DOM 제어 보다 훨씬 효율적으로 처리가 가능 -->
	
	<script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
	<script src="/static/lib.js"></script>	
	<script>
		let vm;
		//뷰 애플리케이션 객체를 생성하고, 원하는 렌더링 영역인 div="app" 와 연결하자
		const app=Vue.createApp({
			/*아래의 data() 메서드는 뷰영역에서 사용할 데이터를 반환하는 역할*/
			data(){
				return{ //뷰 렌더링 영역에서 사용될 데이터를 반환
					//아래의 cartList는 일반 변수가 아니라, data() 함수의 반환대상이 되는 상태변수로써, 
					//Vue 애플리케이션의 렌더링 영역과(즉 MVC에서의 View영역과 직접적으로 연결되어 있는 변수 - 바인딩(binding) 변수)
					cartList:0
				}	
			}, 
			// Vue에서 개발자가 자신만의 메서드를 정의하려면, 아래의 methods 영역에 정의하면된다..
			methods:{
				//나만의 메서드 정의
				moneyFormat(price){
					return format(price);
				},
				
				//장바구니의 아이템 하나를 제거요청
				removeItem(product_id){
					if(confirm("선택하신 아이템을 삭제하시겠어요?")){
						// 비동기 방식으로 삭제 요청~~
						remove(product_id);
					}
				}
				
			}
		});
		
		vm=app.mount("#app");
		
		//비동기 삭제 요청
		function remove(product_id){
			
			//Promise ..
			let p = new Promise((resolve,reject)=>{
				$.ajax({
					url:"/cart/remove",
					method:"Post",
					data: {
						"product_id":product_id
					},
					success:function(result,status,xhr){
						//Promise 에게 성공했음을 알려준다.
						resolve();	//이 호출로 인해 Promise 의 상태값은 fulfilled로 전환
						console.log("성공 결과 정보",result);
						
					},
					error:function(xhr,status,err){	
						reject(err); // 이 호출로 인해 Promise의 상태값은 rejected로 전환
						console.log("실패 결과 정보",err);
					}
				});
			})
			.then(()=>{
				getList();
			})
			.catch((err)=>{
				console.log(err);
			});
			
		}
		
		//비동기 방식으로 장바구니 목록을 요청하기
		function getList(){
			let p = new Promise((resolve, reject)=>{
				//개발자가 원하는 비동기작업=ajax 
				$.ajax({
					url :"/cart/async/list", //RedisCartController 에 요청이 들어감
					method:"GET",
					success:function(result, status, xhr){
						resolve(result); // Promise객체의 상태 값을 fulfilled로 놓기
						//개발자가 Promise의 상태를 resolve() 호출에 의해 fulfilled상태로 놓아놓으면
						//비동기 업무가 종료되었을때, 자동으로 Promise가 지원하는 메서드인 then()을 호출하게 됨
						//또한reject() 호출에 의해서 Promise의 상태가 rejected가 되면, 비동기로직이 완료된 후 자동으로 
						//Promise가 지원하는 메서드 중 catch() 가 호출된다..
						//따라서 개발자는 콜백함수에서 로직을처리하지 않게 되어, 콜백 지옥에서 빠져나올수있다...
					}
				});
			}).then((result)=>{
				console.log("서버에서 가져온 목록은 ",  result);
				vm.cartList = result;
			});	//체이닝 기법 (별도의 변수 선언없이 바로 그 다음 메서드를 연결하는 방식 - 자전거 체인과 유사 )
			
			
			
		}
		
		$(()=>{
			getList();			
		});
		
		function renderList(){

		}
	</script>
	
</body>

</html>