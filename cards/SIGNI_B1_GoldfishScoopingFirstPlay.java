package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.LifeBurstAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B1_GoldfishScoopingFirstPlay extends Card {

    public SIGNI_B1_GoldfishScoopingFirstPlay()
    {
        setImageSets("WX24-P2-075");

        setOriginalName("壱ノ遊　キンギョスクイ");
        setAltNames("イチノユウキンギョスクイ Ichi no Yuu Kingyosukui");
        setDescription("jp",
                "@U：あなたのアタックフェイズ終了時、そのアタックフェイズの間にあなたの＜遊具＞のシグニが場を離れていた場合、このシグニを場からデッキの一番下に置いてもよい。そうした場合、カードを１枚引き、あなたの手札からレベル２以下の＜遊具＞のシグニ１枚をダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。" +
                "~#対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Goldfish Scooping, First Play");
        setDescription("en",
                "@U: At the end of your attack phase, if 1 of your <<Playground Equipment>> SIGNI left your field during that attack phase, you may put this SIGNI from your field on the bottom of your deck. If you do, draw 1 card and you may put 1 level 2 or lower <<Playground Equipment>> SIGNI from your hand onto the field downed. That SIGNI's @E abilities don't activate." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "壹之游 捞金鱼");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段结束时，那个攻击阶段期间你的<<遊具>>精灵离场的场合，可以把这只精灵从场上放置到牌组最下面。这样做的场合，抽1张牌，可以从你的手牌把等级2以下的<<遊具>>精灵1张以横置状态出场。那只精灵的@E能力不能发动。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_END, this::onAutoEff);
            auto1.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_LRIG ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() &&
                GameLog.getPhaseRecordsCount(GamePhase.ATTACK_PRE, event -> event.getId() == GameEventId.MOVE &&
                 CardLocation.isSIGNI(event.getCaller().getLocation()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation()) &&
                 isOwnCard(event.getCaller()) && event.getCaller().getSIGNIClass().matches(CardSIGNIClass.PLAYGROUND_EQUIPMENT)) > 0)
            {
                if(playerChoiceActivate() && returnToDeck(getCardIndex(), DeckPosition.BOTTOM))
                {
                    draw(1);

                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).withLevel(0,2).fromHand().playable()).get();
                    putOnField(cardIndex, Enter.DOWNED | Enter.DONT_ACTIVATE);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
