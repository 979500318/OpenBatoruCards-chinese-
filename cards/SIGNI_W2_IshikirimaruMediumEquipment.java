package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_IshikirimaruMediumEquipment extends Card {
    
    public SIGNI_W2_IshikirimaruMediumEquipment()
    {
        setImageSets("WXDi-P03-050", "SPDi01-59");
        
        setOriginalName("中装　イシキリマル");
        setAltNames("チュウソウイシキリマル Chuusou Ishikirimaru");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニのパワーは＋4000される。\n" +
                "@U $T1：あなたの他のシグニ１体が場に出たとき、あなたのデッキの一番上を見て、それをデッキの一番下に置いてもよい。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Ishikirimaru, High Armed");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI gets +4000 power.\n" +
                "@U $T1: When another SIGNI enters your field, look at the top card of your deck. You may put that card on the bottom of your deck." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Ishikirimaru, Medium Equipment");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI gets +4000 power.\n" +
                "@U $T1: When 1 of your other SIGNI enters the field, look at the top card of your deck, and you may put it on the bottom of your deck." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "中装 石切丸");
        setDescription("zh_simplified", 
                "@C $TP :这只精灵的力量+4000。\n" +
                "@U $T1 :当你的其他的精灵1只出场时，看你的牌组最上面，可以将其放置到牌组最下面。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller != getCardIndex() && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = look();
            if(cardIndex != null) returnToDeck(cardIndex, playerChoiceAction(ActionHint.TOP, ActionHint.BOTTOM) == 1 ? DeckPosition.TOP : DeckPosition.BOTTOM);
        }
        
        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(reveal(cardIndex))
            {
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
