package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_K3_IrohaNatsume extends Card {

    public SIGNI_K3_IrohaNatsume()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-061");
        setLinkedImageSets("WXDi-CP02-TK03A");

        setOriginalName("棗イロハ");
        setAltNames("ナツメイロハ Natsume Iroha");
        setDescription("jp",
                "@E %K：あなたのトラッシュから＜ブルアカ＞のシグニ１枚を対象とし、それを場に出す。\n" +
                "@A @[手札から＜ブルアカ＞のカードを２枚捨てる]@：このシグニの下に《虎丸》が無い場合、クラフトの《虎丸》１つをこのシグニの下に置く。" +
                "~{{A @[アップ状態のルリグ２体をダウンする]@：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらのパワーをそれぞれ－2000する。@@" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Natsume Iroha");
        setDescription("en",
                "@E %K: Put target <<Blue Archive>> SIGNI from your trash onto your field.\n@A @[Discard two <<Blue Archive>> cards]@: If there is no \"Toramaru\" underneath this SIGNI, put a \"Toramaru\" Craft under this SIGNI.~{{A @[Down two upped LRIG]@: Up to two target SIGNI on your opponent's field get --2000 power until end of turn.@@" +
                "~#Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Iroha Natsume");
        setDescription("en_fan",
                "@E %K: Target 1 <<Blue Archive>> SIGNI from your trash, and put it onto the field.\n" +
                "@A @[Discard 2 <<Blue Archive>> cards from your hand]@: If there is no \"Toramaru\" under this SIGNI, put 1 \"Toramaru\" craft under this SIGNI." +
                "~{{A @[Down 2 of your upped LRIG]@: Target up to 2 of your opponent's SIGNI, and until end of turn, they get --2000 power.@@" +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "枣伊吕波");
        setDescription("zh_simplified", 
                "@E %K:从你的废弃区把<<ブルアカ>>精灵1张作为对象，将其出场。\n" +
                "@A 从手牌把<<ブルアカ>>牌2张舍弃:这只精灵的下面没有《虎丸》的场合，衍生的《虎丸》1只放置到这只精灵的下面。\n" +
                "~{{A竖直状态的分身2只横置:对战对手的精灵2只最多作为对象，直到回合结束时为止，这些的力量各-2000。@@" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);

            ActionAbility act = registerActionAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onActionEff1);
            act.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onActionEff2);
            act2.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().SIGNI().under(getCardIndex()).withName("虎丸").getValidTargetsCount() == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            if(new TargetFilter().own().SIGNI().under(getCardIndex()).withName("虎丸").getValidTargetsCount() == 0)
            {
                CardIndex cardIndex = craft(getLinkedImageSets().get(0));

                if(!attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC))
                {
                    exclude(cardIndex);
                }
            }
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.MINUS).OP().SIGNI());
            gainPower(data, -2000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
