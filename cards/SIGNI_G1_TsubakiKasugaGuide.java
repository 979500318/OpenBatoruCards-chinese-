package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_TsubakiKasugaGuide extends Card {

    public SIGNI_G1_TsubakiKasugaGuide()
    {
        setImageSets("WX25-CP1-073");

        setOriginalName("春日ツバキ(ガイド)");
        setAltNames("カスガツバキガイド Kasuga Tsubaki Gaido");
        setDescription("jp",
                "@E：あなたの他の＜ブルアカ＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Kasuga Tsubaki (Guide)");

        setName("en_fan", "Tsubaki Kasuga (Guide)");
        setDescription("en",
                "@E: Target 1 of your other <<Blue Archive>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "春日椿(导游)");
        setDescription("zh_simplified", 
                "@E :你的其他的<<ブルアカ>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerEnterAbility(this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            if(target != null) gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
