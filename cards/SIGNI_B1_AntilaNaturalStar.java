package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_B1_AntilaNaturalStar extends Card {
    
    public SIGNI_B1_AntilaNaturalStar()
    {
        setImageSets("WXDi-P01-063");
        
        setOriginalName("羅星　アンチラ");
        setAltNames("ラセイアンチラ Rasei Anchira");
        setDescription("jp",
                "@E：あなたのトラッシュから#Gを持たないレベル１のシグニを１枚まで対象とし、それをデッキの一番上に置く。" +
                "~#：あなたのデッキの一番上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Antila, Natural Planet");
        setDescription("en",
                "@E: Put up to one target level one SIGNI without a #G from your trash on top of your deck." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Antila, Natural Star");
        setDescription("en_fan",
                "@E: Target up to 1 level 1 SIGNI without #G @[Guard]@ from your trash, and return it to the top of your deck." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "罗星 安琪拉");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有#G的等级1的精灵1张最多作为对象，将其放置到牌组最上面。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.TOP).own().SIGNI().withLevel(1).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            returnToDeck(target, DeckPosition.TOP);
        }
        
        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
