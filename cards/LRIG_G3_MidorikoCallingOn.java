package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_MidorikoCallingOn extends Card {

    public LRIG_G3_MidorikoCallingOn()
    {
        setImageSets("WXDi-P10-008", "WXDi-P10-008U");

        setOriginalName("参上　緑姫");
        setAltNames("サンジョウミドリコ Sanjou Midoriko");
        setDescription("jp",
                "@E：あなたの手札からカードを３枚までエナゾーンに置く。\n" +
                "@A $T1 %G0：シグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。\n" +
                "@A $G1 %G %G %G %X：あなたのエナゾーンからすべてのカードをトラッシュに置く。この効果であなたのトラッシュに置いたカード１枚につき対戦相手は自分のエナゾーンからカードを１枚選びトラッシュに置く。対戦相手のすべてのシグニをトラッシュに置く。"
        );

        setName("en", "Midoriko, Third Entry");
        setDescription("en",
                "@E: Put up to three cards from your hand into your Ener Zone.\n" +
                "@A $T1 %G0: Target SIGNI gets +5000 power until end of turn.\n" +
                "@A $G1 %G %G %G %X: Put all cards in your Ener Zone into your trash. Your opponent chooses a card from their Ener Zone and puts it into their trash for each card put into your trash with this effect. Put all SIGNI on your opponent's field into their owner's trash."
        );
        
        setName("en_fan", "Midoriko, Calling On");
        setDescription("en_fan",
                "@E: Put up to 3 cards from your hand into the ener zone.\n" +
                "@A $T1 %G0: Target 1 SIGNI, and until end of turn, it gets +5000 power.\n" +
                "@A $G1 %G %G %G %X: Put all cards from your ener zone into the trash. For each card put into the trash this way, your opponent chooses 1 card from their ener zone and puts it into the trash. Put all of your opponent's SIGNI into the trash."
        );

		setName("zh_simplified", "参上 绿姬");
        setDescription("zh_simplified", 
                "@E :从你的手牌把牌3张最多放置到能量区。\n" +
                "@A $T1 %G0:精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n" +
                "@A $G1 %G %G %G%X:从你的能量区把全部的牌放置到废弃区。依据这个效果放置到你的废弃区的牌的数量，每有1张对战对手就从自己的能量区选1张牌放置到废弃区。对战对手的全部的精灵放置到废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
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

            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 3) + Cost.colorless(1)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.ENER).own().fromHand());
            putInEner(data);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }

        private void onActionEff2()
        {
            int countTrashed = trash(getCardsInEner(getOwner()));
            
            DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(countTrashed, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
            trash(data);
            
            trash(getSIGNIOnField(getOpponent()));
        }
    }
}
