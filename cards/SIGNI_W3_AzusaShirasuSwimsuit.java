package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_W3_AzusaShirasuSwimsuit extends Card {

    public SIGNI_W3_AzusaShirasuSwimsuit()
    {
        setImageSets("WX25-CP1-038");

        setOriginalName("白洲アズサ(水着)");
        setAltNames("シラスアズサミズギ Shirasu Azusa Mizugi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、対戦相手のシグニ１体を対象とし、それのパワーが5000以下の場合、それを手札に戻す。このターン終了時、それを場からトラッシュに置く。\n" +
                "@A #D：あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、カードを１枚引く。" +
                "~{{E：ターン終了時まで、対戦相手のすべてのシグニは@C能力を失う。"
        );
        
        setName("en", "Shirasu Azusa (Swimsuit)");

        setName("en_fan", "Azusa Shirasu (Swimsuit)");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, target 1 of your opponent's SIGNI, and if its power is 5000 or less, return it to their hand. At the end of this turn, put it into the trash.\n" +
                "@A #D: Reveal the top card of your deck. If it is a <<Blue Archive>> card, draw 1 card." +
                "~{{E: Until end of turn, all of your opponent's SIGNI lose their @C abilities."
        );

		setName("zh_simplified", "白洲梓(泳装)");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，对战对手的精灵1只作为对象，其的力量在5000以下的场合，将其返回手牌。这个回合结束时，将其从场上放置到废弃区。\n" +
                "@A #D:你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，抽1张牌。\n" +
                "~{{E直到回合结束时为止，对战对手的全部的精灵的@C能力失去。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

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
            
            registerActionAbility(new DownCost(), this::onActionEff);

            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
                
                if(target != null)
                {
                    int instanceId = target.getIndexedInstance().getInstanceId();
                    if(target.getIndexedInstance().getPower().getValue() <= 5000)
                    {
                        if(addToHand(target)) return;
                    }
                    
                    ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                    GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/trash", 0.75,3)));
                    callDelayedEffect(record, () -> {
                        if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId)
                        {
                            trash(target);
                        }
                    });
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(!cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) ||
                   draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
        
        private void onEnterEff()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), ability -> ability instanceof ConstantAbility, ChronoDuration.turnEnd());
        }
    }
}
