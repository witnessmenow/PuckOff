package com.ladinc.puckoff.core.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ladinc.puckoff.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.puckoff.core.utilities.GenericEnums.Side;

public class CollisionHelper implements ContactListener{

//	private Sound warriorCollideSound;
//	private Sound fall;
//	
//	public CollisionHelper(Sound collide, Sound fall)
//	{
//		this.warriorCollideSound = collide;
//		this.fall = fall;
//	}
	
	public CollisionHelper()
	{
	}
	
	private Side lastScore;
	public boolean newScore = false;
	
	public Side getLastScored()
	{
		newScore = false;
		return lastScore;
	}
	
	@Override
	public void beginContact(Contact contact) 
	{
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        
        CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
    	CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);

        
        if(bodyAInfo != null && bodyBInfo != null)
        {
        	
        	Gdx.app.debug("beginContact", "between " + bodyAInfo.type.toString() + " and " + bodyBInfo.type.toString());
        	
        	if((bodyAInfo.type == CollisionObjectType.ScoreZone && bodyBInfo.type == CollisionObjectType.Puck) || 
        			(bodyAInfo.type == CollisionObjectType.Puck && bodyBInfo.type == CollisionObjectType.ScoreZone))
        	{

        		//Goal Scored
        		Gdx.app.log("beginContact", "Goal Scored!");
        		
        		if(bodyAInfo.type == CollisionObjectType.ScoreZone)
        			lastScore = bodyAInfo.side;
        		else
        			lastScore = bodyBInfo.side;
        		
        		newScore = true;
        		
        	}
        	//Check for collision of two players
        	if(bodyAInfo.type == CollisionObjectType.Player && bodyBInfo.type == CollisionObjectType.Player)
        	{
        		playerCollideIsDetected(bodyAInfo, bodyBInfo);
        	}
        	
        	
        }
        
		
	}
		
	private void playerCollideIsDetected(CollisionInfo bodyA, CollisionInfo bodyB)
	{
//		if (this.warriorCollideSound != null)
//			this.warriorCollideSound.play(0.5f);
		
		Gdx.app.debug("Collision Helper", "warrior collide");
	}

	@Override
	public void endContact(Contact contact) 
	{
//		Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//		
//        CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
//    	CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);
//    	
//    	if(bodyAInfo != null && bodyBInfo != null)
//        {
//    		if(bodyAInfo.type == CollisionObjectType.playerSensor)
//    		{
//    			if(bodyBInfo.type == CollisionObjectType.Arena)
//    			{
//    				handleFall(bodyAInfo.warrior);
//    				Gdx.app.log("endContact", bodyAInfo.text);
//    			}
//    		}
//    		if(bodyBInfo.type == CollisionObjectType.playerSensor)
//    		{
//    			if(bodyAInfo.type == CollisionObjectType.Arena)
//    			{
//    				handleFall(bodyBInfo.warrior);
//    				
//    				Gdx.app.log("endContact", bodyBInfo.text);
//    			}
//    		}
//        }
        
	}
	
//	private void handleFall(Warrior victim)
//	{
//		if (this.fall != null)
//			this.fall.play(0.9f);
//		
//		victim.inPlay = false;
//		
//		int score = victim.score / 2;
//		
//		if (score < 1)
//			score = 1;
//		
//		if (victim.lastPersonToTouch != null)
//		{		
//			victim.lastPersonToTouch.score = victim.lastPersonToTouch.score + score;
//			
//			Gdx.app.log("SCORE", "Warrior " + victim.lastPersonToTouch.warriorNumber + "Scored " + score + " points and now has " + victim.lastPersonToTouch.score);
//		}
//	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private CollisionInfo getCollisionInfoFromFixture(Fixture fix)
	{	
		CollisionInfo colInfo = null;
		
		if(fix != null)
        {
			Body body = fix.getBody();
			
			if(body != null)
			{
				colInfo = (CollisionInfo) body.getUserData();
			}
        }
		
		return colInfo;
	}

}

