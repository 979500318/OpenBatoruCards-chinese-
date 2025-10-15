package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_G3_VJWOLF3rdVerse extends Card {
    
    public LRIG_G3_VJWOLF3rdVerse()
    {
        setImageSets("WXDi-P03-016", "SPDi07-06","SPDi08-06");
        
        setOriginalName("VJ.WOLF-3rdVerse");
        setAltNames("ブイジェーウルフサードヴァース Bui Jee Urufu Saado Vaasu");
        setDescription("jp",
                "=T ＜Card Jockey＞\n" +
                "^A #D：[[エナチャージ２]]\n" +
                "@E：ターン終了時まで、このルリグは@>@C：あなたのシグニのパワーを＋5000する。@@を得る。\n" +
                "@A $G1 %G0：ターン終了時まで、このルリグは@>@A @[アップ状態のレベル２のルリグ１体をダウンする]@：このルリグをアップする。@@を得る。"
        );
        
        setName("en", "VJ WOLF - 3rd Verse");
        setDescription("en",
                "=T <<Card Jockey>>\n" +
                "^A #D: [[Ener Charge 2]]\n" +
                "@E: This LRIG gains@>@C: SIGNI on your field get +5000 power.@@until end of turn.\n" +
                "@A $G1 %G0: This LRIG gains@>@A Down an upped level two LRIG: Up this LRIG.@@until end of turn."
        );
        
        setName("en_fan", "VJ.WOLF - 3rd Verse");
        setDescription("en_fan",
                "=T <<Card Jockey>>\n" +
                "^A #D: [[Ener Charge 2]]\n" +
                "@E: Until end of turn, this LRIG gains:" +
                "@>@C: Your SIGNI get +5000 power.@@" +
                "@A $G1 %G0: Until end of turn, this LRIG gains:" +
                "@>@A @[Down 1 of your upped level 2 LRIGs]@: Up this LRIG."
        );
        
		setName("zh_simplified", "VJ.WOLF-3rdVerse");
        setDescription("zh_simplified", 
                "=T<<Card:Jockey>>\n" +
                "^A#D:[[能量填充2]]\n" +
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@C :你的精灵的力量+5000。@@\n" +
                "@A $G1 %G0:直到回合结束时为止，这只分身得到\n" +
                "@>@A 竖直状态的等级2的分身1只#D:这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
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
            
            ActionAbility act1 = registerActionAbility(new DownCost(), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            enerCharge(2);
        }
        
        private void onEnterEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(), new PowerModifier(5000));
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        
        private void onActionEff2()
        {
            ActionAbility attachedAction = new ActionAbility(new DownCost(1, new TargetFilter().own().anyLRIG().withLevel(2).upped()), this::onAttachedActionEff);
            attachAbility(getCardIndex(), attachedAction, ChronoDuration.turnEnd());
        }
        private void onAttachedActionEff()
        {
            up();
        }
    }
}
