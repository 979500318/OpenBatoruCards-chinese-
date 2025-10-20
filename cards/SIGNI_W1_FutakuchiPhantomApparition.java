package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_W1_FutakuchiPhantomApparition extends Card {

    public SIGNI_W1_FutakuchiPhantomApparition()
    {
        setImageSets("SPDi01-118");

        setOriginalName("幻怪　フタクチ");
        setAltNames("ゲンカイフタクチ Genkai Futakuchi");
        setDescription("jp",
                "@A @[アップ状態のルリグ１体をダウンする]@：ターン終了時まで、この方法でダウンしたルリグと同じレベルの対戦相手のすべてのシグニは能力を失う。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1ターン終了時まで、対戦相手のすべてのシグニは能力を失う。\n" +
                "$$2カードを２枚引く。"
        );

        setName("en", "Futakuchi, Phantom Apparition");
        setDescription("en",
                "@A @[Down 1 of your upped LRIG]@: Until end of turn, all of your opponent's SIGNI with the same level as the LRIG downed this way lose their abilities." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, all of your opponent's SIGNI lose their abilities.\n" +
                "$$2 Draw 2 cards."
        );

		setName("zh_simplified", "幻怪 二口女");
        setDescription("zh_simplified", 
                "@A 竖直状态的分身1只横置:直到回合结束时为止，与这个方法横置的分身相同等级的对战对手的全部的精灵的能力失去。" +
                "~#以下选1种。\n" +
                "$$1 直到回合结束时为止，对战对手的全部的精灵的能力失去。\n" +
                "$$2 抽2张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new DownCost(new TargetFilter().anyLRIG()), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            int level = ((CardIndexSnapshot)getAbility().getCostPaidData().get()).getLevel().getValue();
            disableAllAbilities(new TargetFilter().OP().SIGNI().withLevel(level).getExportedData(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            } else {
                draw(2);
            }
        }
    }
}
