package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_W3_AkinoAdvancingTowardsCourage extends Card {
    
    public LRIG_W3_AkinoAdvancingTowardsCourage()
    {
        setImageSets("WXDi-P02-009", "SPDi07-02","SPDi08-02","SPDi09-02");
        
        setOriginalName("勇気へ前進　アキノ");
        setAltNames("ユウキヘゼンシンアキノ Yuuki he Zenshin Akino");
        setDescription("jp",
                "=T ＜Ｎｏ　Ｌｉｍｉｔ＞\n" +
                "^C：対戦相手のターンの間、あなたの中央のシグニゾーンにあるシグニのパワーを＋2000する。\n" +
                "@E：カードを１枚引き[[エナチャージ１]]をする。\n" +
                "@A $G1 @[アップ状態のレベル２のルリグ２体をダウンする]@：対戦相手のシグニを２体まで対象とし、手札から#Gを持つカードを１枚捨ててもよい。そうした場合、それらを手札に戻す。"
        );
        
        setName("en", "Akino, Bound for Valor");
        setDescription("en",
                "=T <<No Limit>>\n" +
                "^C: During your opponent's turn, SIGNI in your center SIGNI Zone get +2000 power.\n" +
                "@E: Draw a card and [[Ener Charge 1]].\n" +
                "@A $G1 @[Down two upped level two LRIG]@: You may discard a card with a #G. If you do, return up to two target SIGNI on your opponent's field to their owner's hand."
        );
        
        setName("en_fan", "Akino, Advancing Towards Courage");
        setDescription("en_fan",
                "=T <<No Limit>>\n" +
                "^C: During your opponent's turn, the SIGNI in your center SIGNI zone gets +2000 power.\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 @[Down 2 of your upped level 2 LRIG]@: Target up to 2 of your opponent's SIGNI, and you may discard 1 card with #G @[Guard]@ from your hand. If you do, return them to their hand."
        );
        
		setName("zh_simplified", "向勇气前进 昭乃");
        setDescription("zh_simplified", 
                "=T<<No:Limit>>\n" +
                "^C:对战对手的回合期间，你的中央的精灵区的精灵的力量+2000。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 竖直状态的等级2的分身2只#D对战对手的精灵2只最多作为对象，可以从手牌把持有#G的牌1张舍弃。这样做的场合，将这些返回手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().SIGNI().own(), new PowerModifier(2000));
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new DownCost(2, new TargetFilter().own().anyLRIG().withLevel(2).upped()), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onConstEffCond(CardIndex cardIndex)
        {
            return isLRIGTeam(CardLRIGTeam.NO_LIMIT) && !isOwnTurn() && cardIndex.getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).OP().SIGNI());
            if(data.get() != null && discard(0,1, new TargetFilter().own().SIGNI().withState(CardStateFlag.CAN_GUARD)).get() != null)
            {
                addToHand(data);
            }
        }
    }
}
