package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShoot;

public final class SIGNI_W3_EnergeGreatEquipment extends Card {

    public SIGNI_W3_EnergeGreatEquipment()
    {
        setImageSets("WX24-P1-039");
        setLinkedImageSets("WX24-P1-011");

        setOriginalName("大装　エナジェ");
        setAltNames("タイソウエナジェ Taisou Enaje");
        setDescription("jp",
                "@C：あなたの場に《月日の巫女　タマヨリヒメ》がいるかぎり、このシグニのパワーは＋3000され、このシグニは【シュート】を得る。\n" +
                "@U：この  シグニがバトルによってシグニ１体をバニッシュしたとき、あなたのデッキの上からカードを５枚見る。その中から＜アーム＞のシグニを１枚まで公開し手札に加えるかエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Energe, Great Equipment");
        setDescription("en",
                "@C: As long as your LRIG is \"Tamayorihime, Days Miko\", this SIGNI gets +3000 power and [[Shoot]].\n" +
                "@U: Whenever this SIGNI banishes a SIGNI in battle, look at the top 5 cards of your deck. Reveal up to 1 <<Arm>> SIGNI from among them, and add it to your hand or put into the ener zone, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "大装 源能");
        setDescription("zh_simplified", 
                "@C :你的场上有《月日の巫女　タマヨリヒメ》时，这只精灵的力量+3000，这只精灵得到[[击落]]。\n" +
                "@U :当这只精灵因为战斗把精灵1只破坏时，从你的牌组上面看5张牌。从中把<<アーム>>精灵1张最多公开加入手牌或放置到能量区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000),new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("月日の巫女　タマヨリヒメ") ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShoot());
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).fromLooked()).get();
            if(reveal(cardIndex))
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.ENER) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putInEner(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
