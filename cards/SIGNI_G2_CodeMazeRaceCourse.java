package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G2_CodeMazeRaceCourse extends Card {
    
    public SIGNI_G2_CodeMazeRaceCourse()
    {
        setImageSets("WXDi-P07-084");
        
        setOriginalName("コードメイズ　レースコース");
        setAltNames("コードメイズレースコース Koodo Meizu Reesu Koosu");
        setDescription("jp",
                "@E %G：あなたのデッキの一番下のカードを公開する。そのカードを場に出すかトラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、%X %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Race Course, Code: Maze");
        setDescription("en",
                "@E %G: Reveal the bottom card of your deck. Put that card onto your field or into your trash." +
                "~#You may pay %X %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code Maze Race Course");
        setDescription("en_fan",
                "@E %G: Reveal the bottom card of your deck, and put it onto the field or put it into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X %X. If you do, banish it."
        );
        
		setName("zh_simplified", "迷宫代号 东京赛马场");
        setDescription("zh_simplified", 
                "@E %G:你的牌组最下面的牌公开。那张牌出场或放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以支付%X %X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                int choice = playerChoiceAction(ActionHint.FIELD, ActionHint.TRASH);
                
                if((choice == 1 && !putOnField(cardIndex)) ||
                   (choice == 2 && !trash(cardIndex)))
                {
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                banish(target);
            }
        }
    }
}
