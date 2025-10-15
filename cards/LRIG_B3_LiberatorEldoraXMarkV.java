package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_LiberatorEldoraXMarkV extends Card {

    public LRIG_B3_LiberatorEldoraXMarkV()
    {
        setImageSets("WXDi-P15-007", "WXDi-P15-007U");

        setOriginalName("解放者エルドラ×マークν");
        setAltNames("カイホウシャエルドラマークニュー Kaihousha Erudora Maaku Nyuu Mark v");
        setDescription("jp",
                "@E：カードを２枚引く。\n" +
                "@A $T1 %B0：カードを１枚引くか【エナチャージ１】をする。この能力はあなたの場にあるシグニの下にカードが合計２枚以上ある場合にしか使用できない。\n" +
                "@A $G1 %B0：あなたのトラッシュから＜解放派＞のシグニを２枚まで対象とし、それらをあなたの＜解放派＞のシグニ２体までの下に置く。"
        );

        setName("en", "Liberator Eldora × Mark ν");
        setDescription("en",
                "@E: Draw two cards.\n@A $T1 %B0: Draw a card or [[Ener Charge 1]]. This ability can only be used if there are a total of two or more cards underneath SIGNI on your field.\n@A $G1 %B0: Put up to two target <<Liberation Division>> SIGNI from your trash under up to two <<Liberation Division>> SIGNI on your field."
        );
        
        setName("en_fan", "Liberator Eldora×Mark Nu");
        setDescription("en_fan",
                "@E: Draw 2 cards.\n" +
                "@A $T1 %B0: Draw 1 card or [[Ener Charge 1]]. This ability can only be used if there are a total of 2 or more cards under your SIGNI on the field.\n" +
                "@A $G1 %B0: Target up to 2 <<Liberation Faction>> SIGNI from your trash, and put them under up to 2 <<Liberation Faction>> SIGNI on your field."
        );

		setName("zh_simplified", "解放者艾尔德拉×ν式");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n" +
                "@A $T1 %B0:抽1张牌或[[能量填充1]]。这个能力只有在你的场上的精灵有下面的牌在合计2张以上的场合才能使用。\n" +
                "@A $G1 %B0:从你的废弃区把<<解放派>>精灵2张最多作为对象，将这些放置到你的<<解放派>>精灵2只最多的下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            draw(2);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withUnderType(CardUnderCategory.UNDER).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> dataFromTrash = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash());

            if(dataFromTrash.get() != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION));

                attach(data,dataFromTrash, CardUnderType.UNDER_GENERIC);
            }
        }
    }
}
