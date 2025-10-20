package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B1_ReiTHEDOORNaturalStone extends Card {

    public SIGNI_B1_ReiTHEDOORNaturalStone()
    {
        setImageSets("WXDi-P15-081");

        setOriginalName("羅石　レイ//THE DOOR");
        setAltNames("ラセキレイザドアー Raseki Rei Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのアタックフェイズ開始時、カードを１枚引く。@@を得る。\n" +
                "@E：あなたの場に【ゲート】がある場合、あなたのデッキの上からカードを３枚見る。その中から好きな枚数のカードを好きな順番でデッキの一番下に置き、残りを好きな順番でデッキの一番上に戻す。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Rei//THE DOOR, Natural Crystal");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the beginning of your attack phase, draw a card.@@@E: If there is a [[Gate]] on your field, look at the top three cards of your deck. Put any number of them on the bottom of your deck in any order and the rest on top of your deck in any order." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Rei//THE DOOR, Natural Stone");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the beginning of your attack phase, draw 1 card.@@" +
                "@E: If there is a [[Gate]] on your field, look at the top 3 cards of your deck. Put any number of cards from among them on the bottom of your deck in any order, and return the rest to the top of your deck in any order." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "罗石 令//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的攻击阶段开始时，抽1张牌。@@\n" +
                "@E :你的场上有[[大门]]的场合，从你的牌组上面看3张牌。从中把任意张数的牌任意顺序放置到牌组最下面，剩下的任意顺序返回牌组最上面。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.GEM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            draw(1);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                look(3);
                
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.BOTTOM).own().fromLooked());
                returnToDeck(data, DeckPosition.BOTTOM);
                
                while(getLookedCount() > 0)
                {
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.TOP);
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
