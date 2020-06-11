/***
 * describe Scenario
 */
describe('Check quantity should be more than 0 for 2 Ad of iPhone second page list', () => {
    /**
     * @beforEach cy.server
     */
    beforeEach(() => {
        cy.server()
    });
    /**
     * @cy.route Mocks xhr response
     * @cy.visit goes to url
     * @cy.get body and checks for popUp modal to close it. if not continues
     * @cy.get gets user welcome string. checks if it's visible.
     */
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
   /**
    * @cy.get searchbar by Id and type iphone
    * @cy.wait 4ms
    * @cy.get body, checks for popUp modal to close it. if not continues
    * @cy.get footer and scrollIntoView
    * @cy.get paginator and scrollIntoView
    * @cy.get last ad in the list and scrollIntoView
    * @cy.get Next button. @should contains "Next". clicks on it if so.
    */
   it('search for iphone in the searchbar', () => {
    cy.get('#search-key')
        .type('iphone')
    cy.get('.search-button')
        .click()
    cy.wait(4000)
    cy.get('body').then((body) =>{
        if (body.find('.next-dialog-close').length > 0){
            cy.get('.next-dialog-close').click();
        }
    });
    cy.get('.site-footer').scrollIntoView()
    cy.wait(1000)
    cy.get('.list-pagination').scrollIntoView()
        .wait(1000)
    cy.get('[product-index="59"]').scrollIntoView()
        .wait(1000)
    cy.get('.next-next').should('contain','Next').click()
});
/**
 * @cy.wait 1ms
 * @cy.get second ad @alias secondAd @should be.visible
 * @cy.get @secondAd @find a tag @invoke text 
 * @then text split into array and get the first one
 * @try checks if value is grt than 0
 * @catch @throws Exception. No sold items in the ad.
 */
it('Should see more than 0 sold items in the second Ad of the list', ()=>{
    cy.wait(1000)
    cy.get('[product-index="1"]').as('secondAd')
        .should('be.visible')
    cy.get('@secondAd')
        .find('.sale-value-link')
        .invoke('text')
        .then((text)=>{ 
            var fullText = text;
            fullText = fullText.split(" ")[0]
            try {
                if(fullText > 0){
                    console.log('Sold Items bigger than 0')
                }
            } catch (e) {
                throw new Exception("Sold Items are equal or less than 0")
            }
        });
        
    });
});