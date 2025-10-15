package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class LRIG_R3_DJLOVIT3rdVerseALT extends Card {
    
    public LRIG_R3_DJLOVIT3rdVerseALT()
    {
        setImageSets("WXDi-P06-008", "WXDi-P06-008U");
        
        setOriginalName("DJ.LOVIT 3rdVerse-ALT");
        setAltNames("ディージェーラビットサードヴァースアルト Dii Jee Rabitto Saado Vaasu Aruto");
        setDescription("jp",
                "@U：あなたのターン終了時、対戦相手のエナゾーンにカードが３枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@E：対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@A $G1 %R0：あなたの赤のシグニ１体を対象とし、ターン終了時まで、それは【ダブルクラッシュ】を得る。"
        );
        
        setName("en", "DJ LOVIT - 3rd Verse - ALT");
        setDescription("en",
                "@U: At the end of your turn, if there are three or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@E: Your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@A $G1 %R0: Target red SIGNI on your field gains [[Double Crush]] until end of turn."
        );
        
        setName("en_fan", "DJ.LOVIT 3rd Verse-ALT");
        setDescription("en_fan",
                "@U: At the end of your turn, if there are 3 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "@E: Your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "@A $G1 %R0: Target 1 of your red SIGNI, and until end of turn, it gains [[Double Crush]]."
        );
        
		setName("zh_simplified", "DJ.LOVIT 3rdVerse-ALT");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，对战对手的能量区的牌在3张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@E :对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@A $G1 %R0:你的红色的精灵1只作为对象，直到回合结束时为止，其得到[[双重击溃]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 3)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)).get();
            if(target != null) attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
