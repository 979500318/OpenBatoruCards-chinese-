package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_MerlucciidaeWaterPhantom extends Card {

    public SIGNI_K2_MerlucciidaeWaterPhantom()
    {
        setImageSets("WX24-P3-092");

        setOriginalName("幻水　メルルーサ");
        setAltNames("ゲンスイメルルーサ Gensui Meruruusa");
        setDescription("jp",
                "@A #D @[エナゾーンから＜水獣＞のシグニ１枚をトラッシュに置く]@：あなたのトラッシュから＜水獣＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Merlucciidae, Water Phantom");
        setDescription("en",
                "@A #D @[Put 1 <<Water Beast>> SIGNI from your ener zone into the trash]@: Target 1 <<Water Beast>> SIGNI from your trash, and put it onto the field." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "幻水 无须鳕");
        setDescription("zh_simplified", 
                "@A #D从能量区把<<水獣>>精灵1张放置到废弃区:从你的废弃区把<<水獣>>精灵1张作为对象，将其出场。" +
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.WATER_BEAST).fromEner())), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.WATER_BEAST).fromTrash()).get();
            putOnField(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
