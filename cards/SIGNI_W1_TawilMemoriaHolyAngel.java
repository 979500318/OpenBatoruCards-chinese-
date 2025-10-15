package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_TawilMemoriaHolyAngel extends Card {
    
    public SIGNI_W1_TawilMemoriaHolyAngel()
    {
        setImageSets("WXDi-P07-054", "WXDi-P07-054P");
        
        setOriginalName("聖天　タウィル//メモリア");
        setAltNames("セイテンタウィルメモリア Seiten Tauiru Memoria");
        setDescription("jp",
                "@C：あなたのトラッシュに＜天使＞のシグニが７枚以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@C：対戦相手のターンの間、あなたのトラッシュに白のシグニが７枚以上あるかぎり、このシグニは[[シャドウ（赤）]]を得る。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Tawil//Memoria, Blessed Angel");
        setDescription("en",
                "@C: As long as there are seven or more <<Angel>> SIGNI in your trash, this SIGNI gets +4000 power.\n" +
                "@C: During your opponent's turn, as long as there are seven or more white SIGNI in your trash, this SIGNI gains [[Shadow -- Red]]. " +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Tawil//Memoria, Holy Angel");
        setDescription("en_fan",
                "@C: As long as there are 7 or more <<Angel>> SIGNI in your trash, this SIGNI gets +4000 power.\n" +
                "@C: During your opponent's turn, as long as there are 7 or more white SIGNI in your trash, this SIGNI gains [[Shadow (Red)]]." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "圣天 塔维尔//回忆");
        setDescription("zh_simplified", 
                "@C :你的废弃区的<<天使>>精灵在7张以上时，这只精灵的力量+4000。\n" +
                "@C :对战对手的回合期间，你的废弃区的白色的精灵在7张以上时，这只精灵得到[[暗影（红色）]]。（这只精灵不会被对战对手的红色的能力和效果作为对象）" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(4000));
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().getValidTargetsCount() >= 7 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withColor(CardColor.WHITE).fromTrash().getValidTargetsCount() >= 7 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getColor().matches(CardColor.RED) ? ConditionState.OK : ConditionState.BAD;
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
