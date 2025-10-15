package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_R3_HiranaOneStepTowardsTheGlimmer extends Card {
    
    public LRIG_R3_HiranaOneStepTowardsTheGlimmer()
    {
        setImageSets("WXDi-P05-007");
        
        setOriginalName("煌きへ一歩　ヒラナ");
        setAltNames("キラメきへ一歩ヒラナ Kirameki he Ippo Hirana");
        setDescription("jp",
                "@C：あなたのターンの間、あなたの中央のシグニゾーンにあるシグニのパワーを＋3000する。\n" +
                "@E：カードを１枚引き、[[エナチャージ１]]をする。\n" +
                "@A $G1 %R0：カードを２枚引き[[エナチャージ２]]をする。このターン終了時、あなたの手札とエナゾーンにあるすべてのカードをトラッシュに置く。"
        );
        
        setName("en", "Hirana, a Step Towards the Glimmer");
        setDescription("en",
                "@C: During your turn, SIGNI in your center SIGNI Zone get +3000 power.\n" +
                "@E: Draw a card and [[Ener Charge 1]].\n" +
                "@A $G1 %R0: Draw two cards and [[Ener Charge 2]]. At the end of this turn, put all cards in your hand and Ener Zone into the trash."
        );
        
        setName("en_fan", "Hirana, One Step Towards the Glimmer");
        setDescription("en_fan",
                "@C: During your turn, the SIGNI in your center SIGNI zone gets +3000 power.\n" +
                "@E: Draw 1 card, and [[Ener Charge 1]].\n" +
                "@A $G1 %R0: Draw 2 cards, and [[Ener Charge 2]]. At the end of this turn, put all cards from your hand and ener zone into the trash."
        );
        
		setName("zh_simplified", "向辉煌的一步 平和");
        setDescription("zh_simplified", 
                "@C :你的回合期间，你的中央的精灵区的精灵的力量+3000。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 %R0:抽2张牌并[[能量填充2]]。这个回合结束时，你的手牌和能量区的全部的牌放置到废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
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
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().SIGNI().own().fromLocation(CardLocation.SIGNI_CENTER), new PowerModifier(3000));
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            draw(2);
            enerCharge(2);
            
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                discard(getCardsInHand(getOwner()));
                trash(getCardsInEner(getOwner()));
            });
        }
    }
}
