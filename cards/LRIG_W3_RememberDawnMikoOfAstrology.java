package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class LRIG_W3_RememberDawnMikoOfAstrology extends Card {

    public LRIG_W3_RememberDawnMikoOfAstrology()
    {
        setImageSets("WX24-P3-014", "WX24-P3-014U");

        setOriginalName("占星術の巫女　リメンバ・ドウン");
        setAltNames("センセイジュツノミコリメンバドウン Senseijutsu no Miko Rimenba Daun");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたのデッキの上からカードを４枚見る。その中から＜宇宙＞のシグニを１枚まで公開し手札に加え、残りを好きな順番でデッキの一番上に戻す。\n" +
                "@A $G1 @[@|アストロジ|@]@ %W0：あなたのデッキの一番上を見て、そのカードを裏向きでルリグゾーンに置く。次の対戦相手のアタックフェイズ開始時、そのカードを表向きにしてトラッシュに置き、このターン、対戦相手はそのカードと同じレベルのシグニでアタックできない。"
        );

        setName("en", "Remember Dawn, Miko of Astrology");
        setDescription("en",
                "@U: At the beginning of your main phase, look at the top 4 cards of your deck. Reveal up to 1 <<Space>> SIGNI from among them, add it to your hand, and put the rest on the top of your deck in any order.\n" +
                "@A $G1 @[@|Astrology|@]@ %W0: Look at the top card of your deck, and put it onto the LRIG zone face down. At the beginning of your opponent's next attack phase, put that face down card into your trash, and this turn, your opponent can't attack with SIGNI with the same level as that card."
        );

		setName("zh_simplified", "占星术的巫女 忆·黎明");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，从你的牌组上面看4张牌。从中把<<宇宙>>精灵1张最多公开加入手牌，剩下的任意顺序返回牌组最上面。\n" +
                "@A $G1 占星%W0:看你的牌组最上面，那张牌里向放置到分身区。下一个对战对手的攻击阶段开始时，那张牌表向并放置到废弃区，这个回合，对战对手的与那张牌相同等级的精灵不能攻击。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Astrology");
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            look(4);
            
            CardIndex cardIndex = playerTargetCard(0, 1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.SPACE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = look();
            
            if(putOnZone(cardIndex, CardLocation.LRIG, CardUnderType.ZONE_GENERIC))
            {
                callDelayedEffect(ChronoDuration.nextPhase(getOpponent(), GamePhase.ATTACK_PRE), () -> {
                    if(trash(cardIndex) && CardType.isSIGNI(cardIndex.getCardReference().getType()))
                    {
                        int level = cardIndex.getIndexedInstance().getLevel().getValue();
                        ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_ATTACK, TargetFilter.HINT_OWNER_OP, data ->
                            CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                            data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() == level ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                        ));
                        attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
                    }
                });
            }
        }
    }
}
