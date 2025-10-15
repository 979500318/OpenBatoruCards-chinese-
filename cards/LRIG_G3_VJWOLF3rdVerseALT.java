package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public final class LRIG_G3_VJWOLF3rdVerseALT extends Card {
    
    public LRIG_G3_VJWOLF3rdVerseALT()
    {
        setImageSets("WXDi-P05-008");
        
        setOriginalName("VJ.WOLF 3rdVerse-ALT");
        setAltNames("ブイジェーウルフサードウァ゛ースアルト Bui Jee Urufu Saado Vaasu Aruto");
        setDescription("jp",
                "@E：あなたの場にいるアシストルリグのレベルの合計１につき[[エナチャージ１]]をする。\n" +
                "@A $T1 %G %G %X：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %G0：このターン、あなたのエナゾーンにあるカードは[[マルチエナ]]を得る。"
        );
        
        setName("en", "VJ WOLF - 3rd Verse - ALT");
        setDescription("en",
                "@E: [[Ener Charge 1]] for every level of each of Assist LRIG on your field.\n" +
                "@A $T1 %G %G %X: Vanish target SIGNI on your opponent's field with power 10000 or more.\n" +
                "@A $G1 %G0: Cards in your Ener Zone gain [[Multi Ener]] this turn."
        );
        
        setName("en_fan", "VJ.WOLF 3rd Verse-ALT");
        setDescription("en_fan",
                "@E: [[Ener Charge 1]] for each level among assist LRIGs on your field.\n" +
                "@A $T1 %G %G %X: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it.\n" +
                "@A $G1 %G0: This turn, cards in your ener zone gain [[Multi Ener]]."
        );
        
		setName("zh_simplified", "VJ.WOLF 3rdVerse-ALT");
        setDescription("zh_simplified", 
                "@E :依据你的场上的支援分身的等级的合计的数量，每有1级就[[能量填充1]]。\n" +
                "@A $T1 %G %G%X:对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %G0:这个回合，你的能量区的牌得到[[万花色]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        
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
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            int sum = 0;
            if(getLRIGAssistLeft(getOwner()) != null) sum += getLRIGAssistLeft(getOwner()).getIndexedInstance().getLevel().getValue();
            if(getLRIGAssistRight(getOwner()) != null) sum += getLRIGAssistRight(getOwner()).getIndexedInstance().getLevel().getValue();
            
            enerCharge(sum);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
        
        private void onActionEff2()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().fromEner(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityMultiEner());
        }
    }
}
