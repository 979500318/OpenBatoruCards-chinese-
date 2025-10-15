package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K1_EinMoonlightMushroom extends Card {

    public SIGNI_K1_EinMoonlightMushroom()
    {
        setImageSets("WXDi-P15-096");

        setOriginalName("アイン＝ツキヨタケ");
        setAltNames("アインツキヨタケ Ain Tsukiyotake");
        setDescription("jp",
                "@A @[このシグニを場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Tsukiyotake, Type: Eins");
        setDescription("en",
                "@A @[Put this SIGNI on your field into its owner's trash]@: Target SIGNI on your opponent's field gets --2000 power until end of turn.\n" +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Ein-Moonlight Mushroom");
        setDescription("en_fan",
                "@A @[Put this SIGNI from the field into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "EINS=月夜菌");
        setDescription("zh_simplified", 
                "@A 这只精灵从场上放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new TrashCost(), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
