package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W1_WhiteCloverNaturalPlant extends Card {

    public SIGNI_W1_WhiteCloverNaturalPlant()
    {
        setImageSets("SPDi01-120");

        setOriginalName("羅植　シロツメクサ");
        setAltNames("ラショクシロツメクサ Rashoku Shiro Tsumekusa");
        setDescription("jp",
                "@C $TP：このシグニのパワーはあなたのエナゾーンにあるカードが持つ色の種類１つにつき＋2000される。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "White Clover, Natural Plant");
        setDescription("en",
                "@C $TP: This SIGNI gets +2000 power for each color among cards in your ener zone." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "罗植 白三叶");
        setDescription("zh_simplified", 
                "@C $TP :这只精灵的力量依据你的能量区的牌持有颜色的种类的数量，每有1种就+2000。（无色不含有颜色）" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(this::onConstEffModGetValue));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 2000 * CardAbilities.getColorsCount(getCardsInEner(getOwner()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());

            draw(1);
        }
    }
}
