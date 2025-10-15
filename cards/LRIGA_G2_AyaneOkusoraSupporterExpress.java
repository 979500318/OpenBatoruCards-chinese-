package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;

public final class LRIGA_G2_AyaneOkusoraSupporterExpress extends Card {

    public LRIGA_G2_AyaneOkusoraSupporterExpress()
    {
        setImageSets("WXDi-CP02-040");

        setOriginalName("奥空アヤネ[支援特急便]");
        setAltNames("オクソラアヤネシエントッキュウビン Okusora Ayane Shien Tokkyuubin");
        setDescription("jp",
                "@E：あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。" +
                "~{{E：【エナチャージ１】をする。その後、あなたのエナゾーンから＜ブルアカ＞のカードを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Okusora Ayane [Special Delivery: Combat Supplies]");
        setDescription("en",
                "@E: Shuffle your deck and add the top card of your deck to your Life Cloth.~{{E: [[Ener Charge 1]]. Then, add up to one target <<Blue Archive>> card from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Ayane Okusora [Supporter Express]");
        setDescription("en_fan",
                "@E: Shuffle your deck, and add the top card of your deck to life cloth." +
                "~{{E: [[Ener Charge 1]]. Then, target up to 1 <<Blue Archive>> card from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "奥空绫音[支援特级快递]");
        setDescription("zh_simplified", 
                "@E :你的牌组洗切把最上面的牌加入生命护甲。\n" +
                "~{{E:[[能量填充1]]。然后，从你的能量区把<<ブルアカ>>牌1张最多作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.FORECLOSURE_TASK_FORCE);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            shuffleDeck();
            addToLifeCloth(1);
        }

        private void onEnterEff2()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
            addToHand(target);
        }
    }
}

