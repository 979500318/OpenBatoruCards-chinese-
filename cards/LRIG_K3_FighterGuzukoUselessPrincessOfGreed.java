package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_FighterGuzukoUselessPrincessOfGreed extends Card {

    public LRIG_K3_FighterGuzukoUselessPrincessOfGreed()
    {
        setImageSets("WXDi-P15-009", "WXDi-P15-009U");

        setOriginalName("貪欲の駄姫　闘争者グズ子");
        setAltNames("ドンヨクノダキトウソウシャグズコ Donyoku no Daki Tousousha Guzuko");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュから＜闘争派＞のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $T1 @[手札から＜闘争派＞のシグニを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。\n" +
                "@A $G1 %K0：#C #C #C #C #Cを得る。このゲームの間、あなたは#Cをスペルとシグニにしか支払えない。"
        );

        setName("en", "Warrior Guzuko, Queen of Greed");
        setDescription("en",
                "@E: Put the top three cards of your deck into your trash. Then, add target <<War Division>> SIGNI from your trash to your hand.\n@A $T1 @[Discard a <<War Division>> SIGNI]@: Target SIGNI on your opponent's field gets --5000 power until end of turn.\n@A $G1 %K0: Gain #C #C #C #C #C. You can only pay #C for spells and SIGNI for the duration of the game."
        );
        
        setName("en_fan", "Fighter Guzuko, Useless Princess of Greed");
        setDescription("en_fan",
                "@E: Put the top 3 cards of your deck into the trash. Then, target 1 <<Struggle Faction>> SIGNI from your trash, and add it to your hand.\n" +
                "@A $T1 @[Discard 1 <<Struggle Faction>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.\n" +
                "@A $G1 %K0: Gain #C #C #C #C #C. For the rest of the game, you may only spend #C on spells and SIGNI."
        );

		setName("zh_simplified", "贪欲的驮姬 斗争者迟钝子");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把<<闘争派>>精灵1张作为对象，将其加入手牌。\n" +
                "@A $T1 从手牌把<<闘争派>>精灵1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "@A $G1 %K0得到#C #C #C #C #C。这场游戏期间，你只有把币:用在魔法和精灵才能支付。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        setCoins(2);

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

            ActionAbility act1 = registerActionAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            millDeck(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromTrash()).get();
            addToHand(target);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }

        private void onActionEff2()
        {
            gainCoins(5);

            addPlayerRuleCheck(PlayerRuleCheckType.CAN_SPEND_COINS, getOwner(), ChronoDuration.permanent(), data ->
                data.getSourceCardIndex().getIndexedInstance().matchesTypeByRef(data.getSourceAbility(), CardType.SPELL, CardType.SIGNI,CardType.RESONA) ? RuleCheckState.IGNORE : RuleCheckState.BLOCK
            );
        }
    }
}
