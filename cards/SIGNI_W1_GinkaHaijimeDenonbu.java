package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W1_GinkaHaijimeDenonbu extends Card {

    public SIGNI_W1_GinkaHaijimeDenonbu()
    {
        setImageSets("WXDi-P14-080");

        setOriginalName("電音部　灰島銀華");
        setAltNames("デンオンブハイジマギンカ Denonbu Haijime Ginka");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの場に他の＜電音部＞のシグニがあるかぎり、あなたの＜電音部＞のシグニのパワーを＋2000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "DEN-ON-BU Ginka Haijima");
        setDescription("en",
                "@C: During your opponent's turn, as long as there is another <<DEN-ON-BU>> SIGNI on your field, <<DEN-ON-BU>> SIGNI on your field get +2000 power." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Ginka Haijime, Denonbu");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as there is another <<Denonbu>> SIGNI on your field, all of your <<Denonbu>> SIGNI get +2000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "电音部 灰岛银华");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的场上有其他的<<電音部>>精灵时，你的<<電音部>>精灵的力量+2000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU), new PowerModifier(2000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
