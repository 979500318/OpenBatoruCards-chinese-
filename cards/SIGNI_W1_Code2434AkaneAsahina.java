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

public final class SIGNI_W1_Code2434AkaneAsahina extends Card {

    public SIGNI_W1_Code2434AkaneAsahina()
    {
        setImageSets("WXDi-CP01-035");

        setOriginalName("コード２４３４　朝日南アカネ");
        setAltNames("コードニジサンジアサヒナアカネ Koodo Nijisanji Asahina Akane");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの場に他の＜バーチャル＞のシグニがあるかぎり、あなたの＜バーチャル＞のシグニのパワーを＋2000する。\n" +
                "@C：あなたの場に他の＜世怜音女学院＞のシグニがあるかぎり、このシグニのパワーは＋2000される。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Asahina Akane, Code 2434");
        setDescription("en",
                "@C: During your opponent's turn, as long as there is another <<Virtual>> SIGNI on your field, <<Virtual>> SIGNI on your field get +2000 power.\n@C: As long as there is another <<SELENE Girls' Academy>> SIGNI on your field, this SIGNI gets +2000 power." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Code 2434 Akane Asahina");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as there is another <<Virtual>> SIGNI on your field, all of your <<Virtual>> SIGNI get +2000 power.\n" +
                "@C: As long as there is another <<Selene Girls' Academy>> SIGNI on your field, this SIGNI gets +2000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "2434代号 朝日南茜");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的场上有其他的<<バーチャル>>精灵时，你的<<バーチャル>>精灵的力量+2000。\n" +
                "@C :你的场上有其他的<<世怜音女学院>>精灵时，这只精灵的力量+2000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL, CardSIGNIClass.SELENE_GIRLS_ACADEMY);
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

            registerConstantAbility(this::onConstEff1Cond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL), new PowerModifier(2000));
            registerConstantAbility(this::onConstEff2Cond, new PowerModifier(2000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEff1Cond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onConstEff2Cond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.SELENE_GIRLS_ACADEMY).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
