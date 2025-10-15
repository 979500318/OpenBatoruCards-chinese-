package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_K3_AruRikuhachima extends Card {

    public SIGNI_K3_AruRikuhachima()
    {
        setImageSets("WXDi-CP02-060");

        setOriginalName("陸八魔アル");
        setAltNames("リクハチマアル Rikuhachima Aru");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーをあなたのトラッシュにある＜ブルアカ＞のカード１枚につき－1000する。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。この方法でトラッシュに置かれたシグニのレベルがすべて同じ場合、あなたは手札をすべて捨てる。" +
                "~{{C：[[シャドウ（レベル２以下のシグニ）]]"
        );

        setName("en", "Rikuhachima Aru");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may pay %K. If you do, target SIGNI on your opponent's field gets --1000 power for each <<Blue Archive>> card in your trash until end of turn.\n@E: Put the top three cards of your deck into your trash. If the levels of the SIGNI put into the trash this way are the same, discard your hand.~{{C: [[Shadow -- Level two or less SIGNI]]"
        );
        
        setName("en_fan", "Aru Rikuhachima");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --1000 power for each <<Blue Archive>> card in your trash.\n" +
                "@E: Put the top 3 cards of your deck into the trash. If all SIGNI put into the trash this way have the same level, discard all cards from your hand." +
                "~{{C: [[Shadow (level 2 or lower SIGNI)]]"
        );

		setName("zh_simplified", "陆八魔爱露");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量依据你的废弃区的<<ブルアカ>>的牌的数量，每有1张就-1000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。这个方法放置到废弃区的精灵的等级全部相同的场合，你把手牌全部舍弃。\n" +
                "~{{C:[[暗影（等级2以下的精灵）]]@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                gainPower(target, -1000 * new TargetFilter().own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash().getValidTargetsCount(), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(3);

            int prevLevel = -1;
            for(int i=0;i<data.size();i++)
            {
                CardIndex cardIndex = data.get(i);
                if(!CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef())) continue;
                
                int level = cardIndex.getIndexedInstance().getLevelByRef();
                if(prevLevel != -1 && level != prevLevel) return;
                prevLevel = level;
            }
            if(prevLevel == -1) return;
            
            discard(getCardsInHand(getOwner()));
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
