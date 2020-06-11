describe('Check quantity should be more than 0 for 2 Ad of iPhone second page list', () => {
    beforeEach(() => {
        cy.server()
    });
   it('enter the website of Aliexpress', () => {
        cy.route('GET', '**/api/getUserInfo.do', 'fixture:userInfo.json')
        cy.route('GET', '**/329.htm', '<!--ams-cdp-resource-start cdp.aliexpress.com/327.htm--><!--No Rules Match-->')
        cy.route('GET', '**/60.htm', 'fixture:60.html')
        cy.route('GET', '**/332.htm', 'fixture:332.html')
        cy.route('GET', '**/366.htm', 'fixture:366.html')
        cy.route('GET', '**/388.htm', 'fixture:388.html')
        cy.route('GET', '**/115.htm', '<!--ams-cdp-resource-start cdp.aliexpress.com/327.htm--><!--No Rules Match--><!--ams-cdp-resource-end cdp.aliexpress.com/115.htm-->')
        cy.route('GET', '**/327.htm', '<!--ams-cdp-resource-start cdp.aliexpress.com/327.htm--><!--No Rules Match--><!--ams-cdp-resource-end cdp.aliexpress.com/327.htm-->')
        cy.route('GET', '**/343.htm', 'fixture:343.html')
        cy.route('GET', '**/74.htm', 'fixture:74.html')
        cy.route('GET', '**/setCommonCookie', '{"result":true,"code":0,"message":"success"}')
        cy.route('POST', '**/service/um.json', 'fixture:um.json')
        cy.visit("http://aliexpress.com")
        cy.get('body').then((body) =>{
            if (body.find('.close-layer').length > 0){
                cy.get('.close-layer').click();
            }
        });
        cy.get('[data-role="newuser-welcome"]')
            .should('be.visible')      
   });
});