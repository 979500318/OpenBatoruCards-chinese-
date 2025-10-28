package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.DamageBlockParams;

public final class ARTS_G_EveryonePrepareYourRanks extends Card {

    public ARTS_G_EveryonePrepareYourRanks()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-006", Mask.VERTICAL+"WX25-CP1-006U");

        setOriginalName("諸君、戦列を整えろ");
        setAltNames("ショクンセンレツヲトトノエロ Shoukusen senretsu wo Totonoero");
        setDescription("jp",
                "以下の４つから２つまで選ぶ。\n" +
                "&E４枚以上@0 代わりに３つまで選ぶ。@0" +
                "$$1【エナチャージ３】\n" +
                "$$2あなたのエナゾーンから＜ブルアカ＞のシグニ１枚を選び、そのシグニを場に出す。そのシグニの@E能力は発動しない。\n" +
                "$$3このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。\n" +
                "$$4対戦相手のパワー10000以上のシグニ１体を対象とし、あなたのエナゾーンから＜ブルアカ＞のカード３枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Everyone, Prepare your Ranks!");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "&E4 or more@0 Instead, @[@|choose up to 3 of the following:|@]@ @0" +
                "$$1 [[Ener Charge 3]]\n" +
                "$$2 Choose 1 <<Blue Archive>> SIGNI from your ener zone, and put it onto the field. Its @E abilities don't activate.\n" +
                "$$3 This turn, the next time you would be damaged by a LRIG, instead you aren't damaged.\n" +
                "$$4 Target 1 of your opponent's SIGNI with power 10000 or more, and you may put 3 <<Blue Archive>> cards from your ener zone into the trash. If you do, banish it."
        );

        setName("zh_simplified", "诸位，整列战阵！");
        setDescription("zh_simplified",
                "从以下的4种选2种最多。\n" +
                "&E4张以上@0作为替代，选3种最多。（先选全部的选择项和对象）@0" +
                "$$1 [[能量填充3]]\n" +
                "$$2 从你的能量区选<<蔚蓝档案>>精灵1张，那张精灵出场。那只精灵的@E能力不能发动。\n" +
                "$$3 这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n" +
                "$$4 对战对手的力量10000以上的精灵1只作为对象，可以从你的能量区把<<蔚蓝档案>>牌3张放置到废弃区。这样做的场合，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setOnModesChosenPre(this::onARTSEffPreModeChoice);
            arts.setModeChoice(0,2);
            arts.setRecollect(4);
        }

        private void onARTSEffPreModeChoice()
        {
            arts.setModeChoice(0, arts.isRecollectFulfilled() ? 3 : 2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                enerCharge(3);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner().playable()).get();
                putOnField(target, Enter.DONT_ACTIVATE);
            }
            if((modes & 1<<2) != 0)
            {
                blockNextDamage(DamageBlockParams.ofLRIG());
            }
            if((modes & 1<<3) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();

                if(target != null)
                {
                    DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                    
                    if(trash(data) > 0)
                    {
                        banish(target);
                    }
                }
            }
        }
    }
}
