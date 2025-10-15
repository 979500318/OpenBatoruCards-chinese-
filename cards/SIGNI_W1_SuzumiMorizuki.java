package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_SuzumiMorizuki extends Card {

    public SIGNI_W1_SuzumiMorizuki()
    {
        setImageSets("WXDi-CP02-064");

        setOriginalName("守月スズミ");
        setAltNames("モリヅキスズミ Morizuki Suzumi");
        setDescription("jp",
                "@E %W：あなたの場に他の＜ブルアカ＞のシグニがある場合、ターン終了時まで、対戦相手のすべてのシグニは能力を失う。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1ターン終了時まで、対戦相手のすべてのシグニは能力を失う。\n" +
                "$$2カードを２枚引く。"
        );

        setName("en", "Morizuki Suzumi");
        setDescription("en",
                "@E %W: If there is another <<Blue Archive>> SIGNI on your field, all SIGNI on your opponent's field lose their abilities until end of turn.~{{C: This SIGNI gets +4000 power.@@" +
                "~#Choose one -- \n$$1All SIGNI on your opponent's field lose their abilities until end of turn. \n$$2Draw two cards."
        );
        
        setName("en_fan", "Suzumi Morizuki");
        setDescription("en_fan",
                "@E %W: If there is another <<Blue Archive>> SIGNI on your field, until end of turn, all of your opponent's SIGNI lose their abilities." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, all of your opponent's SIGNI lose their abilities.\n" +
                "$$2 Draw 2 cards."
        );

		setName("zh_simplified", "守月铃美");
        setDescription("zh_simplified", 
                "@E %W:你的场上有其他的<<ブルアカ>>精灵的场合，直到回合结束时为止，对战对手的全部的精灵的能力失去。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#以下选1种。\n" +
                "$$1 直到回合结束时为止，对战对手的全部的精灵的能力失去。\n" +
                "$$2 抽2张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
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
