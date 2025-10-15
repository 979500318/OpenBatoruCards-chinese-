package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_UrithEnmaOfInfiniteHell extends Card {

    public LRIG_K3_UrithEnmaOfInfiniteHell()
    {
        setImageSets("WX24-D5-04");

        setOriginalName("無間地獄の閻魔　ウリス");
        setAltNames("ムゲンジゴクノエンマウリス Mugen Jigoku no Enma Urisu Ulith");
        setDescription("jp",
                "@A $T1 %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。\n" +
                "@A $G1 %K0：あなたのトラッシュから黒のシグニを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Urith, Enma of Infinite Hell");
        setDescription("en",
                "@A $T1 %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "@A $G1 %K0: Target up to 2 black SIGNI from your trash, and add them to your hand."
        );

		setName("zh_simplified", "无间地狱的阎魔 乌莉丝");
        setDescription("zh_simplified", 
                "@A $T1 %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "@A $G1 %K0:从你的废弃区把黑色的精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash());
            addToHand(data);
        }
    }
}
