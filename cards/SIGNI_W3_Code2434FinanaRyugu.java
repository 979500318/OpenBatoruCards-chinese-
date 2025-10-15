package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_W3_Code2434FinanaRyugu extends Card {

    public SIGNI_W3_Code2434FinanaRyugu()
    {
        setImageSets("WXDi-CP01-041");

        setOriginalName("コード２４３４　フィナーナ 竜宮");
        setAltNames("コードニジサンジフィナーナリュウグウ Koodo Nijisanji Finaana Ryuuguu");
        setDescription("jp",
                "@C：あなたの場に他の＜バーチャル＞のシグニがあるかぎり、対戦相手は追加で%Xを支払わないかぎり【ガード】ができない。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Finana Ryugu, Code 2434");
        setDescription("en",
                "@C: As long as there is another <<Virtual>> SIGNI on your field, your opponent cannot [[Guard]] unless they pay an additional %X." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Code 2434 Finana Ryugu");
        setDescription("en_fan",
                "@C: As long as there is another <<Virtual>> SIGNI on your field, your opponent can't [[Guard]] unless they pay %X." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "2434代号 龙宫Finana");
        setDescription("zh_simplified", 
                "@C 你的场上有其他的<<バーチャル>>精灵时，对战对手如果不追加把%X:支付，那么不能[[防御]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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

            registerConstantAbility(this::onConstEffCond, new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD,
                TargetFilter.HINT_OWNER_OP, data -> new EnerCost(Cost.colorless(1)))
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
