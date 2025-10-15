package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_MyuSpangle extends Card {

    public LRIGA_K1_MyuSpangle()
    {
        setImageSets("WXDi-P13-040");

        setOriginalName("ミュウ　－　クロアゲハ");
        setAltNames("ミュウクロアゲハ Myu Kuroageha");
        setDescription("jp",
                "@E：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E：対戦相手のトラッシュからスペルを１枚まで対象とし、それをゲームから除外する。このゲームの間、対戦相手はそれと同じ名前のカードを使用できない。"
        );

        setName("en", "Myu - Black Swallowtail");
        setDescription("en",
                "@E: Add target SIGNI without a #G from your trash to your hand.\n@E: Remove up to one target spell in your opponent's trash from the game. Your opponent cannot use a card with the same name as that spell for the duration of the game."
        );
        
        setName("en_fan", "Myu - Spangle");
        setDescription("en_fan",
                "@E: Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand.\n" +
                "@E: Target up to 1 spell from your opponent's trash, and exclude it from the game. For the rest of the game, your opponent can't use cards with the same name."
        );

		setName("zh_simplified", "缪-黑扬羽蝶");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "@E :从对战对手的废弃区把魔法1张最多作为对象，将其从游戏除外。这场游戏期间，对战对手不能把与其相同名字的牌使用。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.EXCLUDE).OP().spell().fromTrash()).get();
            
            if(exclude(cardIndex))
            {
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_USE_SPELL, getOpponent(), ChronoDuration.permanent(), data ->
                    data.getSourceCardIndex().getIndexedInstance().getName().getValue().contains(cardIndex.getCardReference().getOriginalName()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                );
            }
        }
    }
}
