package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIGA_B2_AsunaIchinoseHereIGo extends Card {

    public LRIGA_B2_AsunaIchinoseHereIGo()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-033");

        setOriginalName("一之瀬アスナ[行っくよー！]");
        setAltNames("イチノセアスナイックヨー Ichinose Asuna Ikku Yoo");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U：対戦相手のシグニ１体がアタックしたとき、あなたはデッキの一番上のカードをチェックゾーンに置く。そのカードがアタックしたそのシグニと同じレベルのシグニの場合、そのアタックを無効にする。そのカードをチェックゾーンからトラッシュに置く。@@を得る。\n" +
                "@E %B：あなたのデッキの上からカードを５枚見る。それらのカードを好きな順番でデッキの一番上に戻す。" +
                "~{{E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Ichinose Asuna [On My Way!]");
        setDescription("en",
                "@E: This LRIG gains@>@U: Whenever a SIGNI on your opponent's field attacks, put the top card of your deck into your Check Zone. If that card is a SIGNI with the same level as the attacking SIGNI, negate that attack. Put that card from the Check Zone into its owner's trash.@@until end of turn.\n@E %B: Look at the top five cards of your deck. Put them on top of your deck in any order.~{{E @[Discard a <<Blue Archive>> card]@: Draw two cards."
        );
        
        setName("en_fan", "Asuna Ichinose [Here I Go!]");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U: Whenever 1 of your opponent's SIGNI attacks, put the top card of your deck into the check zone. If that card is a SIGNI with the same level as the attacking SIGNI, disable that attack. Put that card from the check zone into the trash.@@" +
                "@E %B: Look at the top 5 cards of your deck. Return them to the top of your deck in any order." +
                "~{{E @[Discard 1 <<Blue Archive>> card from your hand]@: Draw 2 cards."
        );

		setName("zh_simplified", "一之濑明日奈[要冲了哦！]");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U :当对战对手的精灵1只攻击时，你把牌组最上面的牌放置到检查区。那张牌与攻击的那只精灵是相同等级的精灵的场合，那次攻击无效。那张牌从检查区放置到废弃区。@@\n" +
                "@E %B:从你的牌组上面看5张牌。这些牌任意顺序返回牌组最上面。\n" +
                "~{{E从手牌把<<ブルアカ>>牌1张舍弃:抽2张牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CC);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff2);

            EnterAbility enter3 = registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff3);
            enter3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getIndexedInstance().getTypeByRef()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = putInCheckZone(CardLocation.DECK_MAIN);
            
            if(cardIndex != null)
            {
                if(CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getLevelByRef() == caller.getIndexedInstance().getLevelByRef())
                {
                    disableNextAttack(caller);
                }
                
                if(cardIndex.getLocation() == CardLocation.CHECK_ZONE) trash(cardIndex);
            }
        }

        private void onEnterEff2()
        {
            look(5);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onEnterEff3()
        {
            draw(2);
        }
    }
}

